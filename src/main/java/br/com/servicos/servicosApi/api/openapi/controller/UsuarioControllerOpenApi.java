package br.com.servicos.servicosApi.api.openapi.controller;

import javax.servlet.http.HttpServletRequest;

import br.com.servicos.servicosApi.api.exceptionhandler.Problem;
import br.com.servicos.servicosApi.api.model.request.AtualizacaoUsuarioRequest;
import br.com.servicos.servicosApi.api.model.request.UsuarioRequest;
import br.com.servicos.servicosApi.api.model.response.UsuarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Retorna usuario logado")
	public UsuarioResponse retornaUsuarioLogado(
			@ApiParam(value = "Barrer Token no header Autentication", example = "Representação de um token de login", required = true) 
			HttpServletRequest request);
	
	@ApiOperation("Cadastra um usuario")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário cadastrado"),
	})
	public UsuarioResponse adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) 
			UsuarioRequest request);
	
	@ApiOperation("Atualiza um usuário pelo Token")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public UsuarioResponse atualiza(
			@ApiParam(value = "Barrer Token no header Autentication", example = "Representação de um token de login", required = true) 
			HttpServletRequest request, 
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true)
			AtualizacaoUsuarioRequest usuarioRequest);
	
	@ApiOperation("Deleta um usuário pelo Token")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário excluido"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public void remover(
			@ApiParam(value = "Barrer Token no header Autentication", example = "Representação de um token de login", required = true) 
			HttpServletRequest request);
	
}
