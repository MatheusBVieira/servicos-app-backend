package br.com.servicos.servicosApi.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long> {
	
	Page<Servico> findByCategoria(Categoria categoria, Pageable paginacao);
	
}
