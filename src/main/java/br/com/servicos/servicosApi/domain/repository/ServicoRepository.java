package br.com.servicos.servicosApi.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long> {
	
//	@Query(value = "select new Servico(avg(a.nota) as notaMedia, s.id as id,s.titulo as titulo,s.descricao as descricao, s.categoria as categoria, s.prestadorServico as prestadorServico ) from Avaliacao a join Servico s on s.categoria=:categoria")
	@Query(value = "select new Servico(COALESCE(avg(a.nota),0) as notaMedia, s) from Avaliacao a right join Servico s on s.categoria=:categoria")
	Page<Servico> findByCategoriaComNota(@Param("categoria")Categoria categoria, Pageable paginacao);
	
}
