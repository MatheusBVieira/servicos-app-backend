package br.com.servicos.servicosApi.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoResponse {

	private Long id;
	private String nome;
	private String estado;
	
}
