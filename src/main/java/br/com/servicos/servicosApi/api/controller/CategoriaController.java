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

import br.com.servicos.servicosApi.api.assembler.CategoriaRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.CategoriaResponseAssembler;
import br.com.servicos.servicosApi.api.model.request.CategoriaRequest;
import br.com.servicos.servicosApi.api.model.response.CategoriaResponse;
import br.com.servicos.servicosApi.api.openapi.controller.CategoriaControllerOpenApi;
import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.repository.CategoriaRepository;
import br.com.servicos.servicosApi.domain.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements CategoriaControllerOpenApi{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaResponseAssembler categoriaResponseAssembler;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private CategoriaRequestDisassembler categoriaRequestDisassembler;

	@GetMapping
	public List<CategoriaResponse> listar(@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 20) Pageable paginacao) {
		Page<Categoria> categorias = categoriaRepository.findAll(paginacao);
		
		List<Categoria> categoriasList = categorias.getContent();
		
		return categoriaResponseAssembler.toCollectionResponse(categoriasList);
	}
	
	@GetMapping("/{categoriaId}")
	public CategoriaResponse buscar(@PathVariable Long categoriaId) {
		Categoria categoria = categoriaService.buscarOuFalhar(categoriaId);
		return categoriaResponseAssembler.toResponse(categoria);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaResponse adicionar(@RequestBody @Valid CategoriaRequest categoriaRequest) {
		Categoria categoria = categoriaRequestDisassembler.toDomainObject(categoriaRequest);
		categoria = categoriaService.salvar(categoria);
		
		return categoriaResponseAssembler.toResponse(categoria);
	}
	
	@PutMapping("/{categoriaId}")
	public CategoriaResponse atualizar(@PathVariable Long categoriaId, @RequestBody @Valid CategoriaRequest categoriaRequest) {
		Categoria categoriaAtual = categoriaService.buscarOuFalhar(categoriaId);

		categoriaRequestDisassembler.copyToDomainObject(categoriaRequest, categoriaAtual);
		
		categoriaAtual = categoriaService.salvar(categoriaAtual);

		return categoriaResponseAssembler.toResponse(categoriaAtual);
	}
	
	@DeleteMapping("/{categoriaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long categoriaId) {
		categoriaService.excluir(categoriaId);
	}
	
}
