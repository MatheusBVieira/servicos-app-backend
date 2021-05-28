package br.com.servicos.servicosApi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"), 
	CONSTRAIN_VIOLATION("/constrain-violation", "Problema com a query do banco de dados");
	
	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://servicos.com.br" + path;
		this.title = title;
	}
}
