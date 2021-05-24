package br.com.servicos.servicosApi.api.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoRequest {

	private Float nota;
	private String comentario;
	
}
