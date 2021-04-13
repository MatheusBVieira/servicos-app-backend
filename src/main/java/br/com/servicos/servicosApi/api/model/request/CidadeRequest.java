package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.model.Estado;
import lombok.Data;

@Data
public class CidadeRequest {

	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdInput estado;

	public Cidade converter() {
		Estado estado2 = new Estado();
		estado2.setId(estado.getId());
		
		return new Cidade(nome, estado2);
	}
	
}
