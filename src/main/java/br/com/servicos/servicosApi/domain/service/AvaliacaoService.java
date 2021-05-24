package br.com.servicos.servicosApi.domain.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.api.model.response.MediaResponse;
import br.com.servicos.servicosApi.domain.exception.AvaliacaoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.model.Avaliacao;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {

	private static final String MSG_AVALIACAO_EM_USO = "Avaliação de código %d não pode ser removida, pois está em uso";

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
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

}
