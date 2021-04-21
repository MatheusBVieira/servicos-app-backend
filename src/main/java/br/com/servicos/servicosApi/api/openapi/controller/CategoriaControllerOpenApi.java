package br.com.servicos.servicosApi.api.openapi.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.servicos.servicosApi.api.exceptionhandler.Problem;
import br.com.servicos.servicosApi.api.model.request.CategoriaRequest;
import br.com.servicos.servicosApi.api.model.response.CategoriaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Categoria")
public interface CategoriaControllerOpenApi {
	
	@ApiOperation("Lista as categorias")
	List<CategoriaResponse> listar(@ApiParam(value = "Deve passar paginação caso queira alterar a padrão", example = "Representação de uma paginação") Pageable paginacao);
	
	@ApiOperation("Busca uma categoria por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da categoria inválida", response = Problem.class),
		@ApiResponse(code = 404, message = "Categoria não encontrada", response = Problem.class)
	})
	CategoriaResponse buscar(
			@ApiParam(value = "ID de uma categoria", example = "1", required = true)
			Long categoriaId);
	
	@ApiOperation("Cadastra uma categoria")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Categoria cadastrada"),
	})
	CategoriaResponse adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova categoria", required = true)
			CategoriaRequest categoriaRequest);
	
	@ApiOperation("Atualiza uma categoria por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria atualizada"),
		@ApiResponse(code = 404, message = "Categoria não encontrada", response = Problem.class)
	})
	CategoriaResponse atualizar(
			@ApiParam(value = "ID de uma Categoria", example = "1", required = true) 
			Long categoriaId,
			
			@ApiParam(name = "corpo", value = "Representação de uma Categoria com os novos dados", required = true)
			CategoriaRequest categoriaRequest);
	
	@ApiOperation("Exclui uma Categoria por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Categoria excluída"),
		@ApiResponse(code = 404, message = "Categoria não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de uma Categoria", example = "1", required = true)
			Long categoriaId);
}
