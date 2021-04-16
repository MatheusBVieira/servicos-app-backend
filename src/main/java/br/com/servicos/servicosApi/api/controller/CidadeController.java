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

import br.com.servicos.servicosApi.api.assembler.CidadeRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.CidadeResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.CidadeRequest;
import br.com.servicos.servicosApi.api.model.response.CidadeResponse;
import br.com.servicos.servicosApi.api.openapi.controller.CidadeControllerOpenApi;
import br.com.servicos.servicosApi.domain.exception.EstadoNaoEncontradoException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.repository.CidadeRepository;
import br.com.servicos.servicosApi.domain.service.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeResponseAssembler cidadeReponseAssembler;
	
	@Autowired
	private CidadeRequestDisassembler cidadeRequestDisassembler;

	@Override
	@GetMapping
	public List<CidadeResponse> listar(
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 20) Pageable paginacao) {
		Page<Cidade> cidades = cidadeRepository.findAll(paginacao);
		return cidadeReponseAssembler.toCollectionResponse(cidades.getContent());
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeResponse buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		return cidadeReponseAssembler.toResponse(cidade);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeResponse adicionar(@RequestBody @Valid CidadeRequest cidadeRequest) {
		try {
			Cidade cidade = cidadeRequestDisassembler.toDomainObject(cidadeRequest);
			
			cidade = cidadeService.salvar(cidade);
			
			return cidadeReponseAssembler.toResponse(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeResponse atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeRequest cidadeRequest) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

			cidadeRequestDisassembler.copyToDomainObject(cidadeRequest, cidadeAtual);

			cidadeAtual = cidadeService.salvar(cidadeAtual);
			
			return cidadeReponseAssembler.toResponse(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}

}
