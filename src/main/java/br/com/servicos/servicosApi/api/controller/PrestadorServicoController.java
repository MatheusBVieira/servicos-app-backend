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

import br.com.servicos.servicosApi.api.assembler.PrestadorRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.PrestadorResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.AtualizacaoPrestadorRequest;
import br.com.servicos.servicosApi.api.model.request.PrestadorRequest;
import br.com.servicos.servicosApi.api.model.response.PrestadorResponse;
import br.com.servicos.servicosApi.domain.model.PrestadorServico;
import br.com.servicos.servicosApi.domain.service.PrestadorService;

@RestController
@RequestMapping("prestador")
public class PrestadorServicoController {

	@Autowired 
	private PrestadorService prestadorService;
	
	@Autowired
	private PrestadorResponseAssembler prestadorResponseAssembler;
	
	@Autowired
	private PrestadorRequestDisassembler prestadorRequestDisassembler;
	
	@GetMapping
	public PrestadorResponse retornaPrestadorLogado(HttpServletRequest request) {
		PrestadorServico prestador = prestadorService.getOne(request);
		return prestadorResponseAssembler.toResponse(prestador);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PrestadorResponse adicionar(@RequestBody @Valid PrestadorRequest request) {
		PrestadorServico prestador = prestadorRequestDisassembler.toDomainObject(request);
		prestador = prestadorService.insere(prestador);
		return prestadorResponseAssembler.toResponse(prestador);
	}
	
	@PutMapping
	public PrestadorResponse atualiza(HttpServletRequest request,
			@RequestBody @Valid AtualizacaoPrestadorRequest prestadorRequest) {
		PrestadorServico usuarioAtual = prestadorService.getOne(request);
		prestadorRequestDisassembler.copyToDomainObject(prestadorRequest, usuarioAtual);
		usuarioAtual = prestadorService.insere(usuarioAtual);

		return prestadorResponseAssembler.toResponse(usuarioAtual);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(HttpServletRequest request) {
		prestadorService.excluir(request);
	}
	
}
