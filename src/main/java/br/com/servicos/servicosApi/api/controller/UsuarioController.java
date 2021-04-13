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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.servicos.servicosApi.api.assembler.UsuarioInputDisassembler;
import br.com.servicos.servicosApi.api.assembler.UsuarioResponseAssembler;
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

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@Autowired
	private UsuarioResponseAssembler usuarioResponseAssembler;

	@GetMapping
	public UsuarioResponse retornaUsuarioLogado(HttpServletRequest request) {
		Usuario usuario = usuarioService.getOne(request);
		return usuarioResponseAssembler.toResponse(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse cadastrar(@RequestBody @Valid UsuarioRequest request, UriComponentsBuilder uriBuilder) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(request);
		usuario = usuarioService.insere(usuario);
		return usuarioResponseAssembler.toResponse(usuario);
	}

	@PutMapping
	public UsuarioResponse atualiza(HttpServletRequest request, @RequestBody @Valid AtualizacaoUsuarioRequest usuarioRequest)
			throws Exception {
		Usuario usuarioAtual = usuarioService.getOne(request);
		usuarioInputDisassembler.copyToDomainObject(usuarioRequest, usuarioAtual);
		usuarioAtual = usuarioService.insere(usuarioAtual);
		
		return usuarioResponseAssembler.toResponse(usuarioAtual);

	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(HttpServletRequest request) {
		usuarioService.excluir(request);
	}

}