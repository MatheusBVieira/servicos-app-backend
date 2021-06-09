package br.com.servicos.servicosApi.api.openapi.controller;

import org.springframework.http.ResponseEntity;

import br.com.servicos.servicosApi.api.model.request.LoginRequest;
import br.com.servicos.servicosApi.api.model.response.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Autenticação")
public interface AutenticacaoControllerOpenApi {

	@ApiOperation("Fazer login")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Barrer token"),
		@ApiResponse(code = 400, message = "Usuario não encontrado")
	})
	public ResponseEntity<TokenResponse> autenticar(
			@ApiParam(value = "corpo", example = "Representação de login", required = true)
			LoginRequest request);
}
