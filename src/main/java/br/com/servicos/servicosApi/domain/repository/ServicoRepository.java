package br.com.servicos.servicosApi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import br.com.servicos.servicosApi.domain.model.Servico;

public interface ServicoRepository extends CustomJpaRepository<Servico, Long>, ServicoRepositoryQueries {
	
	@Query(value = "select new Servico(s, COALESCE(avg(a.nota),0) as notaMedia) from Servico s left join Avaliacao a on s.id=:servicoId where s.id=:servicoId group by s.id")
	Optional<Servico> findByIdComNota(Long servicoId);
	
}
