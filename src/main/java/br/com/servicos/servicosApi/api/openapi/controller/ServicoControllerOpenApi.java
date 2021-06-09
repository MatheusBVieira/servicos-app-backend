package br.com.servicos.servicosApi.api.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import br.com.servicos.servicosApi.api.exceptionhandler.Problem;
import br.com.servicos.servicosApi.api.model.request.ServicoRequest;
import br.com.servicos.servicosApi.api.model.response.ServicoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Serviços")
public interface ServicoControllerOpenApi {

	@ApiOperation("Lista os serviços")
	List<ServicoResponse> listar(
			@ApiParam(value = "ID de uma categoria", example = "1", required = false)
			Long servicoId,
			@ApiParam(value = "Deve passar paginação caso queira alterar a padrão", example = "Representação de uma paginação") Pageable paginacao);
	
	@ApiOperation("Busca um serviço por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de serviço inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
	})
	ServicoResponse buscar(
			@ApiParam(value = "ID de um serviço", example = "1", required = true)
			Long servicoId);
	
	@ApiOperation("Cadastra um serviço")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Serviço cadastrado"),
	})
	ServicoResponse adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo serviço", required = true)
			ServicoRequest servicoRequest,
			@ApiParam(value = "Barrer Token no header Autentication", example = "Representação de um token de login", required = true) 
			HttpServletRequest request);
	
	@ApiOperation("Atualiza um Serviço por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Serviço atualizado"),
		@ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
	})
	ServicoResponse atualizar(
			@ApiParam(value = "ID de um Serviço", example = "1", required = true) 
			Long servicoId,
			@ApiParam(name = "corpo", value = "Representação de um Serviço com os novos dados", required = true)
			ServicoRequest servicoRequest,
			@ApiParam(value = "Barrer Token no header Autentication", example = "Representação de um token de login", required = true) 
			HttpServletRequest request);
	
	@ApiOperation("Exclui um Serviço por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Serviço excluído"),
		@ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um Serviço", example = "1", required = true)
			Long servicoId);
	
}
