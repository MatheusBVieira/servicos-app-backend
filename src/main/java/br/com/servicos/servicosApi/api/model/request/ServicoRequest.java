package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ServicoRequest {
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	private String descricao;
	
	@Valid
	@NotNull
	private CategoriaIdRequest categoria;
	
	
}
