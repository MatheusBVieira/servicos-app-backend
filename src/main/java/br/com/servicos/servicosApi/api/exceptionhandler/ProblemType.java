package br.com.servicos.servicosApi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"), 
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://servicos.com.br" + path;
		this.title = title;
	}
}
