package br.com.servicos.servicosApi.domain.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	
	@Transient
	private Double notaMedia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario prestadorServico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	public Servico(Double notaMedia, Servico servico)	{
		this.notaMedia = notaMedia;
		this.id = servico.getId();
		this.titulo = servico.getTitulo();
		this.descricao = servico.getDescricao();
		this.categoria = servico.getCategoria();
		this.prestadorServico = servico.getPrestadorServico();
	}
	
	public boolean isNovo() {
		return getId() == null;
	}
	
}
