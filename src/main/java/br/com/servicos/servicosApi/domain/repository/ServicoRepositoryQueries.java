package br.com.servicos.servicosApi.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepositoryQueries {

	List<Servico> find(@Param("categoria")Categoria categoria, @Param("latitude")Double latitude, @Param("longitude")Double longitude, Pageable paginacao);
	
}
