package br.com.servicos.servicosApi.api.model.request;

import javax.validation.constraints.NotBlank;

import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.model.Endereco;
import lombok.Data;

@Data
public class EnderecoRequest {

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;
	
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	private Cidade cidade;

	public Endereco converter() {
		return new Endereco(cep, logradouro, numero, complemento, bairro, cidade);
	}
}
