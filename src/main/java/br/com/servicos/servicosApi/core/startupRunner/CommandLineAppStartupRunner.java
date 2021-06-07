package br.com.servicos.servicosApi.core.startupRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;
import br.com.servicos.servicosApi.domain.util.UsuarioUtil;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) throws Exception {

		boolean exists = usuarioRepository.existsUsuarioByEmail("admin@admin.com");

		if (!exists) {
			Usuario admin = UsuarioUtil.criaAdmin("&bGWCy^sYg1V");
			usuarioRepository.save(admin);
		}
	}

	
}
