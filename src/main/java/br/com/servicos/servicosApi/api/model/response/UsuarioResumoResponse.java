package br.com.servicos.servicosApi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResumoResponse {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Matheus Bruggemann Vieira")
	private String nomeCompleto;
	
}
