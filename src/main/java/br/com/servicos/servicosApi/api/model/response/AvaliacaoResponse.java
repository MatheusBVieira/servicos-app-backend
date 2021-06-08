package br.com.servicos.servicosApi.api.model.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoResponse {
	
	private Long id;
	private Float nota;
	private String comentario;
	private LocalDateTime data;
	private UsuarioResumoResponse avaliador;
	private ServicoResumoResponse servico;
	
}
