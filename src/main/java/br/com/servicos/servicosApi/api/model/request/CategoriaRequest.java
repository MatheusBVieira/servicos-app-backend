package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {

	@NotBlank
	private String categoria;
	
}
