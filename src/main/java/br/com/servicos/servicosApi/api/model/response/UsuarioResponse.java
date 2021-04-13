package br.com.servicos.servicosApi.api.model.response;

import lombok.Data;

@Data
public class UsuarioResponse {
	
	private Long id;
	private String nomeCompleto;
	private String cpf;
	private String email;
	private String telefone;
	private EnderecoResponse endereco;
}
