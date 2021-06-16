package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EnderecoRequest {

	@ApiModelProperty(example = "88063160", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua das corticeiras", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "114", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "Apto 901")
	private String complemento;
	
	@ApiModelProperty(example = "Campeche", required = true)
	@NotBlank
	private String bairro;
	
	@NotNull
	private Double latitude;
	
	@NotNull
	private Double longitude;
	
	@Valid
	@NotNull
	private CidadeIdRequest cidade;

}
