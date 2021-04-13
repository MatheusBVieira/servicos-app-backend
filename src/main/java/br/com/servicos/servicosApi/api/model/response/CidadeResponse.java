package br.com.servicos.servicosApi.api.model.response;

import br.com.servicos.servicosApi.domain.model.Estado;
import lombok.Data;

@Data
public class CidadeResponse {
	
	private Long id;
	private String nome;
	private Estado estado;
	
}
