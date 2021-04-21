package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EstadoRequest {
	
	@NotBlank
	private String nome;

}
