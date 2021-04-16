package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AtualizacaoUsuarioRequest {

	@ApiModelProperty(example = "Matheus Bruggemann Vieira", required = true)
	@NotBlank
	@Length(min = 5)
	private String nomeCompleto;
	
	@ApiModelProperty(example = "matheusbvieira@hotmail.com", required = true)
	@Email
	@NotBlank
	@Length(min = 10)
	private String email;
	
	@ApiModelProperty(example = "48991466688", required = true)
	@NotBlank
	@Length(min = 11)
	private String telefone;
	
	private EnderecoRequest endereco;
	
}
