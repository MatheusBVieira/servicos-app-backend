package br.com.servicos.servicosApi.domain.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.api.model.response.MediaResponse;
import br.com.servicos.servicosApi.domain.exception.AvaliacaoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.exception.EntidadeNaoEncontradaException;
import br.com.servicos.servicosApi.domain.model.Avaliacao;
import br.com.servicos.servicosApi.domain.model.LiberaAvaliacao;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.AvaliacaoRepository;
import br.com.servicos.servicosApi.domain.repository.LiberaAvaliacaoRepository;

@Service
public class AvaliacaoService {

	private static final String MSG_AVALIACAO_EM_USO = "Avaliação de código %d não pode ser removida, pois está em uso";
	private static final String MSG_LIBERACAO_NAO_ENCONTRADA = "Não existe liberação de avaliação do servico de id %d para o usuário de id %d";
	private static final String MSG_AVALIACAO_NAO_ENCONTRADA = "Não existe um cadastro de avaliação de código %d";
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LiberaAvaliacaoRepository liberaAvalicaoRepository;
	
	public Avaliacao salvar(Avaliacao avaliacao, HttpServletRequest request, Long servicoId) {
		avaliacaoRepository.detach(avaliacao);
		
		if (avaliacao.isNovo()) {
			Servico servico = servicoService.buscarOuFalhar(servicoId);
			avaliacao.setServico(servico);
			
			Usuario avaliador = usuarioService.getOne(request);
			avaliacao.setAvaliador(avaliador);
			
			avaliacao.setData(LocalDateTime.now());
		}
		
		return avaliacaoRepository.save(avaliacao);
	}
	
	public void salvar(@Valid LiberaAvaliacao liberaAvaliacao, HttpServletRequest request) {
		liberaAvalicaoRepository.detach(liberaAvaliacao);
		Servico servico = servicoService.buscarOuFalhar(liberaAvaliacao.getServico().getId());
		Usuario usuario = usuarioService.getOne(request);
		
		boolean podeComentar = liberaAvaliacao.isPodeComentar();
		try {
			liberaAvaliacao = verificaLiberacao(servico.getId(), usuario, servico);
		} catch (EntidadeNaoEncontradaException e) {
			liberaAvaliacao.setServico(servico);
			liberaAvaliacao.setUsuario(usuario);
		}
		
		liberaAvaliacao.setPodeComentar(podeComentar);
		
		
		liberaAvalicaoRepository.save(liberaAvaliacao);
	}

	public Page<Avaliacao> listaPorServicoId(Pageable paginacao, Long servicoId) {
		servicoService.buscarOuFalhar(servicoId);
		return avaliacaoRepository.findByServicoId(servicoId, paginacao);
	}

	public MediaResponse retornaMedia(Long servicoId) {
		Float avg = avaliacaoRepository.returnAvg(servicoService.buscarOuFalhar(servicoId));
		MediaResponse media = new MediaResponse();
		media.setMedia(avg);
		return media;
	}

	public void excluir(Long avaliacaoId) {
		try {
			avaliacaoRepository.deleteById(avaliacaoId);
		} catch (EmptyResultDataAccessException e) {
			throw new AvaliacaoNaoEncontradoException(avaliacaoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_AVALIACAO_EM_USO, avaliacaoId));
		}
	}

	public Avaliacao buscarOuFalhar(Long avaliacaoId) {
		return avaliacaoRepository.findById(avaliacaoId).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_AVALIACAO_NAO_ENCONTRADA, avaliacaoId)));
	}

	public void podeComentar(Long servicoId, HttpServletRequest request) {
		Usuario usuario = usuarioService.getOne(request);
		Servico servico = servicoService.buscarOuFalhar(servicoId);
		
		LiberaAvaliacao liberacao = verificaLiberacao(servicoId, usuario, servico);
		if (!liberacao.isPodeComentar()) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_LIBERACAO_NAO_ENCONTRADA, servicoId, usuario.getId()));
		}
	}

	private LiberaAvaliacao verificaLiberacao(Long servicoId, Usuario usuario, Servico servico) {
		LiberaAvaliacao liberacao = liberaAvalicaoRepository.findByUsuarioIdAndServicoId(usuario.getId(), servico.getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_LIBERACAO_NAO_ENCONTRADA, servicoId, usuario.getId())));
		return liberacao;
	}

	

}
