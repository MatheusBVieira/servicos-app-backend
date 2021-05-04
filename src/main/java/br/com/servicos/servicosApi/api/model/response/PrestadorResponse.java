package br.com.servicos.servicosApi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class PrestadorResponse {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Matheus Bruggemann Vieira")
	private String nomeCompleto;

	@ApiModelProperty(example = "01014310903")
	private String cpf;

	@ApiModelProperty(example = "matheusbvieira@hotmail.com")
	private String email;

	@ApiModelProperty(example = "48991466688")
	private String telefone;

	private EnderecoResponse endereco;
}
