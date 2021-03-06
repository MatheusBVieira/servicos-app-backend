package br.com.servicos.servicosApi.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.servicos.servicosApi.api.assembler.ServicoRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.ServicoResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.ServicoRequest;
import br.com.servicos.servicosApi.api.model.response.ServicoResponse;
import br.com.servicos.servicosApi.api.openapi.controller.ServicoControllerOpenApi;
import br.com.servicos.servicosApi.domain.exception.EstadoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.model.Servico;
import br.com.servicos.servicosApi.domain.service.ServicoService;

@RestController
@RequestMapping(value = "/servicos")
public class ServicoController implements ServicoControllerOpenApi {

	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ServicoResponseAssembler servicoResponseAssembler;
	
	@Autowired
	private ServicoRequestDisassembler servicoRequestDisassembler;
	
	@GetMapping
	public List<ServicoResponse> listar(
			@RequestParam(required = false) Long categoriaId,
			@RequestParam(required = false) Long prestadorId,
			@RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 20) Pageable paginacao) {
		
		List<Servico> servicos = servicoService.lista(categoriaId, prestadorId, latitude, longitude, paginacao);
		
		return servicoResponseAssembler.toCollectionResponse(servicos);
	}


	
	@Override
	@GetMapping("/{servicoId}")
	public ServicoResponse buscar(@PathVariable Long servicoId) {
		Servico servico = servicoService.buscarOuFalhar(servicoId);
		return servicoResponseAssembler.toResponse(servico);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoResponse adicionar(@RequestBody @Valid ServicoRequest servicoRequest, HttpServletRequest request) {
		try {
			Servico servico = servicoRequestDisassembler.toDomainObject(servicoRequest);
			
			servico = servicoService.salvar(servico, request);
			
			return servicoResponseAssembler.toResponse(servico);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{servicoId}")
	public ServicoResponse atualizar(@PathVariable Long servicoId, @RequestBody @Valid ServicoRequest servicoRequest, HttpServletRequest request) {
		try {
			Servico servicoAtual = servicoService.buscarOuFalhar(servicoId);

			servicoRequestDisassembler.copyToDomainObject(servicoRequest, servicoAtual);

			servicoAtual = servicoService.salvar(servicoAtual, request);
			
			return servicoResponseAssembler.toResponse(servicoAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@DeleteMapping("/{servicoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long servicoId) {
		servicoService.excluir(servicoId);
	}
	
	
}
