package br.com.servicos.servicosApi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "servico_agendado")
public class ServicoAgendado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Servico servico;
	
	@OneToOne
	private Usuario contratante;
	
	private LocalDateTime dataPedido;
	private LocalDateTime dataVisita;
	
	private Boolean isOrcamento;
	private BigDecimal precoOrcamento;
 	private BigDecimal precoServico;
	
	
}
