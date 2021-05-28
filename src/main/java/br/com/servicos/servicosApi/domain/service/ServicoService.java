package br.com.servicos.servicosApi.domain.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.exception.ServicoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.PrestadorServico;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.repository.ServicoRepository;

@Service
public class ServicoService {

	private static final String MSG_USUARIO_EM_USO = "Servico de código %d não pode ser removida, pois está em uso";

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private PrestadorService prestadorService;

	@Autowired
	private CategoriaService categoriaService;

	public Servico salvar(Servico servico, HttpServletRequest request) {
		servicoRepository.detach(servico);

		Categoria categoria = categoriaService.buscarOuFalhar(servico.getCategoria().getId());
		servico.setCategoria(categoria);

		if (servico.isNovo()) {
			PrestadorServico prestador = prestadorService.getOne(request);
			servico.setPrestadorServico(prestador);
		}

		return servicoRepository.save(servico);
	}

	public Servico buscarOuFalhar(Long servicoId) {
		return servicoRepository.findById(servicoId).orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));
	}

	public void excluir(Long servicoId) {
		try {
			servicoRepository.deleteById(servicoId);

		} catch (EmptyResultDataAccessException e) {
			throw new ServicoNaoEncontradoException(servicoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, servicoId));
		}
	}

	public Page<Servico> listaPorCategoria(Pageable paginacao, Long categoriaId) {
		if (categoriaId != null) {
			Categoria categoria = categoriaService.buscarOuFalhar(categoriaId);
			return servicoRepository.findByCategoriaComNota(categoria, paginacao);
		} else {
			return servicoRepository.findAll(paginacao);
		}
	}

}
