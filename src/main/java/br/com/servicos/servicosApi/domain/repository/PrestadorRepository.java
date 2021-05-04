package br.com.servicos.servicosApi.domain.repository;

import java.util.Optional;

import br.com.servicos.servicosApi.domain.model.PrestadorServico;
import br.com.servicos.servicosApi.domain.model.Usuario;

public interface PrestadorRepository extends CustomJpaRepository<PrestadorServico, Long> {

	Optional<Usuario> findByEmail(String email);

}
