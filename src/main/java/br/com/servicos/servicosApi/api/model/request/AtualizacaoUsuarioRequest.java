package br.com.servicos.servicosApi.api.model.request;

import lombok.Data;

@Data
public class AtualizacaoUsuarioRequest {

	private String nomeCompleto;
	private String email;
	private String telefone;
	private EnderecoRequest endereco;
	
}
