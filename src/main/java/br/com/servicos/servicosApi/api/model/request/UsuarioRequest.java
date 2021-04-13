package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class UsuarioRequest {
	
	@NotBlank
	@Length(min = 5)
	private String nomeCompleto;

	@CPF
	@NotBlank
	private String cpf;

	@NotBlank
	@Length(min = 8)
	private String senha;

	@Email
	@NotBlank
	@Length(min = 10)
	private String email;

	@NotBlank
	@Length(min = 11)
	private String telefone;
	
	@Valid
	@NotNull
	private EnderecoRequest endereco;

	
}
