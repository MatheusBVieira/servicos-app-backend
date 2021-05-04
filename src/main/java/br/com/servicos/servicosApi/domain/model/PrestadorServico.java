package br.com.servicos.servicosApi.domain.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "prestador_servico")
@EqualsAndHashCode(callSuper = true)
public class PrestadorServico extends Usuario {
	private static final long serialVersionUID = 1L;

	@OneToMany
	private List<ServicoAgendado> servicos;
	
	@OneToMany
	private List<Avaliacao> avaliacoes;
	
}
