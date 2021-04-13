package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EnderecoRequest {

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;
	
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdRequest cidade;

}
