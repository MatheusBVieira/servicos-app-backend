package br.com.servicos.servicosApi.api.model.request;

import br.com.servicos.servicosApi.domain.model.Estado;
import lombok.Data;

@Data
public class EstadoRequest {
	
	private String nome;

	public Estado converter() {
		return new Estado(nome);
	}

}
