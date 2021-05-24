package br.com.servicos.servicosApi.domain.service;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.exception.UsuarioNaoEncontradoException;
import br.com.servicos.servicosApi.domain.model.Perfil;
import br.com.servicos.servicosApi.domain.model.PrestadorServico;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.PrestadorRepository;

@Service
public class PrestadorService {

	private static final String MSG_PRESTADOR_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private PrestadorRepository prestadorRepository;

	public PrestadorServico getOne(HttpServletRequest request) {
		Long id = usuarioService.getIdUsuario(request);
		return getOne(id);
	}

	public PrestadorServico getOne(Long id) {
		return prestadorRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	public PrestadorServico insere(PrestadorServico prestador) {
		prestadorRepository.detach(prestador);

		prestador.getEndereco().setCidade(cidadeService.buscarOuFalhar(prestador.getEndereco().getCidade().getId()));
		
		Optional<Usuario> prestadorExistente = prestadorRepository.findByEmail(prestador.getEmail());

		if (prestadorExistente.isPresent() && !prestadorExistente.get().equals(prestador)) {
			throw new NegocioException(
					String.format("Já existe um prestador cadastrado com o e-mail %s", prestador.getEmail()));
		}

		if (prestador.isNovo()) {
			Perfil perfil = new Perfil();
			perfil.setNome("ROLE_PRESTADOR");
			prestador.setPerfis(Arrays.asList(perfil));
			prestador.setSenha(BCrypt.hashpw(prestador.getSenha(), BCrypt.gensalt()));
		}

		return prestadorRepository.save(prestador);
	}

	public void excluir(HttpServletRequest request) {
		Long id = usuarioService.getIdUsuario(request);
		try {
			prestadorRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PRESTADOR_EM_USO, id));
		}
	}
}
