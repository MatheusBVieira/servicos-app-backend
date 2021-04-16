package br.com.servicos.servicosApi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoResponse {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Florianópolis")
	private String nome;
	
	@ApiModelProperty(example = "Santa Catarina")
	private String estado;
	
}
