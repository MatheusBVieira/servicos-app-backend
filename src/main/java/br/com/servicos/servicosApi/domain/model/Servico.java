package br.com.servicos.servicosApi.domain.model;

import javax.persistence.Column;
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
	
	@Column(name = "distancia_maxima")
	private Double distanciaMaxima;

	@Transient
	private Double notaMedia;

	@Transient
	private Double distanciaKM;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario prestadorServico;

	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;

	public Servico(Servico servico, Double notaMedia) {
		this.notaMedia = notaMedia;
		this.id = servico.getId();
		this.titulo = servico.getTitulo();
		this.descricao = servico.getDescricao();
		this.distanciaMaxima = servico.getDistanciaMaxima();
		this.categoria = servico.getCategoria();
		this.prestadorServico = servico.getPrestadorServico();
	}

	public Servico(Servico servico, Double notaMedia, Double distanciaKM) {
		this.notaMedia = notaMedia;
		this.distanciaKM = distanciaKM;
		this.id = servico.getId();
		this.titulo = servico.getTitulo();
		this.descricao = servico.getDescricao();
		this.distanciaMaxima = servico.getDistanciaMaxima();
		this.categoria = servico.getCategoria();
		this.prestadorServico = servico.getPrestadorServico();
	}

	public boolean isNovo() {
		return getId() == null;
	}

}
