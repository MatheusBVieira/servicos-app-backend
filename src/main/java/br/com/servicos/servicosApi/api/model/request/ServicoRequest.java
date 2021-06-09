package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServicoRequest {
	
	@ApiModelProperty(example = "Servico de faxineira", required = true)
	@NotBlank
	private String titulo;
	
	@ApiModelProperty(example = "Trabalho a 20 anos como faxineira", required = true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "1", required = true)
	@Valid
	@NotNull
	private CategoriaIdRequest categoria;
	
	
}
