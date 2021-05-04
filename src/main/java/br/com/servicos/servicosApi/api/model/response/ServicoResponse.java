package br.com.servicos.servicosApi.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoResponse {
	
	private Long id;
	private String titulo;
	private String descricao;
	private PrestadorResponse prestadorServico;
	private CategoriaResponse categoria;

}
