package br.com.servicos.servicosApi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoResponse {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Servico de faxineira")
	private String titulo;
	
	@ApiModelProperty(example = "Trabalho a 20 anos como faxineira")
	private String descricao;
	
	@ApiModelProperty(example = "4.2")
	private Double notaMedia;
	
	private UsuarioResponse prestadorServico;
	private CategoriaResponse categoria;

}
