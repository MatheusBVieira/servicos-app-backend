package br.com.servicos.servicosApi.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.servicos.servicosApi.api.model.request.AtualizacaoUsuarioRequest;
import br.com.servicos.servicosApi.api.model.request.UsuarioRequest;
import br.com.servicos.servicosApi.api.model.response.UsuarioResponse;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public UsuarioResponse retornaUsuarioLogado(HttpServletRequest request) {
		return new UsuarioResponse(usuarioService.getOne(request));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse cadastrar(@RequestBody @Valid UsuarioRequest request, UriComponentsBuilder uriBuilder) {
		Usuario usuario = usuarioService.insere(request);
		return new UsuarioResponse(usuario);
	}

	@PatchMapping
	public UsuarioResponse atualiza(HttpServletRequest request,
			@RequestBody @Valid AtualizacaoUsuarioRequest form) throws Exception {

		Usuario usuarioAtualizado = usuarioService.atualiza(request, form);
		return new UsuarioResponse(usuarioAtualizado);

	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(HttpServletRequest request) {
		usuarioService.excluir(request);
	}

}