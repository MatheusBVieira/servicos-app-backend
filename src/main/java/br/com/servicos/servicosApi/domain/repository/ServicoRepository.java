package br.com.servicos.servicosApi.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long> {
	
	@Query(value = "select new Servico(COALESCE(avg(a.nota),0) as notaMedia, s) from Servico s left join Avaliacao a on s.categoria=:categoria group by s.id")
	Page<Servico> findByCategoriaComNota(@Param("categoria")Categoria categoria, Pageable paginacao);
	
}
