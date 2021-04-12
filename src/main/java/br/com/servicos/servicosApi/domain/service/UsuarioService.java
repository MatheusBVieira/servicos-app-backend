package br.com.servicos.servicosApi.domain.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.api.model.request.AtualizacaoUsuarioRequest;
import br.com.servicos.servicosApi.api.model.request.UsuarioRequest;
import br.com.servicos.servicosApi.core.security.TokenService;
import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
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

	private Usuario getOne(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));
	}

	private Long getIdUsuario(HttpServletRequest request) {
		return tokenService.getIdUsuario(request);
	}

	public Usuario getOne(HttpServletRequest request) {
		Long idUsuario = getIdUsuario(request);
		return getOne(idUsuario);
	}

	public Usuario insere(UsuarioRequest request) {
		Usuario usuario = request.converter();
		return usuarioRepository.save(usuario);
	}

	public Usuario atualiza(HttpServletRequest request, AtualizacaoUsuarioRequest form) throws Exception {
		Usuario usuario = this.getOne(request);
		Usuario usuarioAtualizado = form.atualizar(usuario, usuarioRepository);
		return usuarioAtualizado;
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
