package br.com.servicos.servicosApi.api.openapi.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.servicos.servicosApi.api.exceptionhandler.Problem;
import br.com.servicos.servicosApi.api.model.request.EstadoRequest;
import br.com.servicos.servicosApi.api.model.response.EstadoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	List<EstadoResponse> listar(@ApiParam(value = "Deve passar paginação caso queira alterar a padrão", example = "Representação de uma paginação") Pageable paginacao);


	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoResponse buscar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId);

	@ApiOperation("Cadastra um estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado cadastrado"),
	})
	EstadoResponse adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
			EstadoRequest estadoInput);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atualizado"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoResponse atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId,
			
			@ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
			EstadoRequest estadoInput);

	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado excluído"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long estadoId);

}
