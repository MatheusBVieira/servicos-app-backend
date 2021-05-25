package br.com.servicos.servicosApi.api.model.request;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MidiaRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Lob
	@Basic(optional = false, fetch = FetchType.LAZY)
	private byte[] media;
	private String tipo;
	
	@Column(unique = true)
	private String nome;
	private String descricao;
	private LocalDate dataDeUpload;
	
}
