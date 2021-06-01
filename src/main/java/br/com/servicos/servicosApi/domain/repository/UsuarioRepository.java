package br.com.servicos.servicosApi.domain.repository;

import java.util.Optional;

import br.com.servicos.servicosApi.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
	boolean existsUsuarioByEmail(String email);

}
