package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SenhaRequest {
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	@Length(min = 8)
	private String senhaAtual;
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	@Length(min = 8)
	private String novaSenha;
}
