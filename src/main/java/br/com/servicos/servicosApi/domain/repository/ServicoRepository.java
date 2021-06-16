package br.com.servicos.servicosApi.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long>, ServicoRepositoryQueries {
	
	@Query(value = "select new Servico(s, COALESCE(avg(a.nota),0) as notaMedia) from Servico s left join Avaliacao a on s.id=:servicoId where s.id=:servicoId group by s.id")
	Optional<Servico> findByIdComNota(Long servicoId);
	
	@Query(value = "select new Servico(s, COALESCE(avg(a.nota),0) as notaMedia) from Servico s left join Avaliacao a on s.prestadorServico.id=:prestadorServico where s.id=:prestadorServico group by s.id")
	Page<Servico> findByPrestadorServicoId(Long prestadorServico, Pageable paginacao);
	
}
