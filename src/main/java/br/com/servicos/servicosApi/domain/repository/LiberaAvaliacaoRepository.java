package br.com.servicos.servicosApi.domain.repository;

import java.util.Optional;

import br.com.servicos.servicosApi.domain.model.LiberaAvaliacao;

public interface LiberaAvaliacaoRepository extends CustomJpaRepository<LiberaAvaliacao, Long>{


	Optional<LiberaAvaliacao> findByUsuarioIdAndServicoId(Long id, Long id2);

}
