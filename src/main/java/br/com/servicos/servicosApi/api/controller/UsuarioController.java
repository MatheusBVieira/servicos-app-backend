package br.com.servicos.servicosApi.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.servicos.servicosApi.api.assembler.UsuarioRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.UsuarioResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.AtualizacaoUsuarioRequest;
import br.com.servicos.servicosApi.api.model.request.UsuarioRequest;
import br.com.servicos.servicosApi.api.model.response.UsuarioResponse;
import br.com.servicos.servicosApi.api.openapi.controller.UsuarioControllerOpenApi;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRequestDisassembler usuarioInputDisassembler;

	@Autowired
	private UsuarioResponseAssembler usuarioResponseAssembler;

	@Override
	@GetMapping
	public UsuarioResponse retornaUsuarioLogado(HttpServletRequest request) {
		Usuario usuario = usuarioService.getOne(request);
		return usuarioResponseAssembler.toResponse(usuario);
	}

	// TODO retorno null no nome da cidade e estado.
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse adicionar(@RequestBody @Valid UsuarioRequest request) {
		
		Usuario usuario = usuarioInputDisassembler.toDomainObject(request);
		usuario = usuarioService.insere(usuario, request.getIsPrestador());
		return usuarioResponseAssembler.toResponse(usuario);
	}

	@Override
	@PutMapping
	public UsuarioResponse atualiza(HttpServletRequest request,
			@RequestBody @Valid AtualizacaoUsuarioRequest usuarioRequest) {
		Usuario usuarioAtual = usuarioService.getOne(request);
		usuarioInputDisassembler.copyToDomainObject(usuarioRequest, usuarioAtual);
		usuarioAtual = usuarioService.insere(usuarioAtual, false);

		return usuarioResponseAssembler.toResponse(usuarioAtual);

	}

	@Override
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(HttpServletRequest request) {
		usuarioService.excluir(request);
	}

}