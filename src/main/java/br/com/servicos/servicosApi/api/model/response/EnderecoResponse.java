package br.com.servicos.servicosApi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoResponse {
	
	@ApiModelProperty(example = "88063160")
	private String cep;
	
	@ApiModelProperty(example = "Rua das corticeiras")
	private String logradouro;
	
	@ApiModelProperty(example = "114")
	private String numero;
	
	@ApiModelProperty(example = "Apto 901")
	private String complemento;
	
	@ApiModelProperty(example = "Campeche")
	private String bairro;
	
	private CidadeResumoResponse cidade;
	
}
