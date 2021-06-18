package br.com.servicos.servicosApi.api.model.request;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiberaAvaliacaoRequest {

	@Valid
	private ServicoIdRequest servico;
	
	private boolean podeComentar;
}
