package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsuarioRequest {
	
	@ApiModelProperty(example = "Matheus Bruggemann Vieira", required = true)
	@NotBlank
	@Length(min = 5)
	private String nomeCompleto;

	@ApiModelProperty(example = "01014310903", required = true)
	@CPF
	@NotBlank
	private String cpf;

	@ApiModelProperty(example = "12345678", required = true)
	@NotBlank
	@Length(min = 8)
	private String senha;

	@ApiModelProperty(example = "matheusbvieira@hotmail.com", required = true)
	@Email
	@NotBlank
	@Length(min = 10)
	private String email;

	@ApiModelProperty(example = "48991466688", required = true)
	@NotBlank
	@Length(min = 11)
	private String telefone;
	
	@Valid
	@NotNull
	private EnderecoRequest endereco;

	
}
