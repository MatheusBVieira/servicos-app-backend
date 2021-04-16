package br.com.servicos.servicosApi.api.model.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.swagger.annotations.ApiModelProperty;

public class LoginRequest {
	
	@ApiModelProperty(example = "matheusbvieira@hotmail.com", required = true)
	private String email;
	
	@ApiModelProperty(example = "12345678", required = true)
	private String senha;

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
