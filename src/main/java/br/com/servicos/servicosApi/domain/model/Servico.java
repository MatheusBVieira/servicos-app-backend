package br.com.servicos.servicosApi.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servico")
@Data
@NoArgsConstructor
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	
	@ManyToOne
	private PrestadorServico prestadorServico;
	
	@ManyToOne
	private Categoria categoria;
	
	public boolean isNovo() {
		return getId() == null;
	}
	
}
