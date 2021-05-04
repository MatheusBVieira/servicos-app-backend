package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaIdRequest {

	@NotNull
	private Long id;
	
}
