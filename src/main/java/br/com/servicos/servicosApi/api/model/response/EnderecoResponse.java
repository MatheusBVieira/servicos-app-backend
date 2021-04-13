package br.com.servicos.servicosApi.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoResponse {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeResumoResponse cidade;
	
}
