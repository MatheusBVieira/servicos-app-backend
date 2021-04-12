package br.com.servicos.servicosApi.api.model.response;

public class TokenResponse {

	private String token;
	private String tipo;

	public TokenResponse(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

}
