package br.com.servicos.servicosApi.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.servicos.servicosApi.domain.model.Avaliacao;
import br.com.servicos.servicosApi.domain.model.Servico;

public interface AvaliacaoRepository extends CustomJpaRepository<Avaliacao, Long>{

	Page<Avaliacao> findByServicoId(Long servicoId, Pageable paginacao);

	@Query("select avg(nota) FROM Avaliacao a where a.servico=:servicoId")
	Float returnAvg(@Param("servicoId") Servico servicoId);

	
}
