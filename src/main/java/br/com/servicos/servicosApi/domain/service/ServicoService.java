package br.com.servicos.servicosApi.domain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.exception.ServicoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.ServicoRepository;

@Service
public class ServicoService {

	private static final String MSG_USUARIO_EM_USO = "Servico de código %d não pode ser removida, pois está em uso";

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private UsuarioService prestadorService;

	@Autowired
	private CategoriaService categoriaService;

	public Servico salvar(Servico servico, HttpServletRequest request) {
		servicoRepository.detach(servico);

		Categoria categoria = categoriaService.buscarOuFalhar(servico.getCategoria().getId());
		servico.setCategoria(categoria);

		if (servico.isNovo()) {
			Usuario prestador = prestadorService.getOne(request);
			servico.setPrestadorServico(prestador);
		}

		return servicoRepository.save(servico);
	}

	public Servico buscarOuFalhar(Long servicoId) {
		return servicoRepository.findByIdComNota(servicoId).orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));
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

	public List<Servico> lista(Long categoriaId, Long prestadorId, Double latitude, Double longitude, Pageable paginacao) {
		if (verificaDoisId(categoriaId, prestadorId)) {
			throw new NegocioException("Por favor informe categoriaId ou prestadorId");
		}
		
		if (verificaId(prestadorId)) {
			Categoria categoria = categoriaService.buscarOuFalhar(categoriaId);
			return servicoRepository.find(categoria, latitude, longitude, paginacao);
		} else {
			prestadorService.getOne(prestadorId);
			return servicoRepository.findByPrestadorServicoId(prestadorId, paginacao).getContent();
		}
	}

	private boolean verificaDoisId(Long categoriaId, Long prestadorId) {
		return verificaId(prestadorId) && verificaId(categoriaId);
	}

	private boolean verificaId(Long id) {
		return id == null;
	}

}
