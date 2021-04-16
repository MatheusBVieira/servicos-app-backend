package br.com.servicos.servicosApi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.servicos.servicosApi.api.assembler.EstadoRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.EstadoResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.EstadoRequest;
import br.com.servicos.servicosApi.api.model.response.EstadoResponse;
import br.com.servicos.servicosApi.api.openapi.controller.EstadoControllerOpenApi;
import br.com.servicos.servicosApi.domain.model.Estado;
import br.com.servicos.servicosApi.domain.repository.EstadoRepository;
import br.com.servicos.servicosApi.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoResponseAssembler estadoResponseAssembler;
	
	@Autowired
	private EstadoRequestDisassembler estadoRequestDisassembler;

	@GetMapping
	public List<EstadoResponse> listar(
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 20) Pageable paginacao) {
		Page<Estado> estados = estadoRepository.findAll(paginacao);
		
		List<Estado> estadosList = estados.getContent();
		
		return estadoResponseAssembler.toCollectionResponse(estadosList);
	}

	@GetMapping("/{estadoId}")
	public EstadoResponse buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		return estadoResponseAssembler.toResponse(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoResponse adicionar(@RequestBody @Valid EstadoRequest estadoRequest) {
		Estado estado = estadoRequestDisassembler.toDomainObject(estadoRequest);
		estado = estadoService.salvar(estado);
		
		return estadoResponseAssembler.toResponse(estado);
	}

	@PutMapping("/{estadoId}")
	public EstadoResponse atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequest estadoRequest) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);

		estadoRequestDisassembler.copyToDomainObject(estadoRequest, estadoAtual);
		
		estadoAtual = estadoService.salvar(estadoAtual);

		return estadoResponseAssembler.toResponse(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}

}
