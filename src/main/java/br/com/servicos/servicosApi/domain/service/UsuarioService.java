package br.com.servicos.servicosApi.domain.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.core.security.TokenService;
import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.exception.UsuarioNaoEncontradoException;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CidadeService cidadeService;

	private Usuario getOne(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));
	}

	public Long getIdUsuario(HttpServletRequest request) {
		return tokenService.getIdUsuario(request);
	}

	public Usuario getOne(HttpServletRequest request) {
		Long idUsuario = getIdUsuario(request);
		return getOne(idUsuario);
	}
	
	@Transactional
	public Usuario insere(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		cidadeService.buscarOuFalhar(usuario.getEndereco().getCidade().getId());
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
		}
		
		return usuarioRepository.save(usuario);
	}


	public void excluir(HttpServletRequest request) {
		Long id = this.getIdUsuario(request);
		try {
			usuarioRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
		}
	}

}
