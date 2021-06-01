package br.com.servicos.servicosApi.core.startupRunner;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.domain.model.Endereco;
import br.com.servicos.servicosApi.domain.model.Perfil;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) throws Exception {

		boolean exists = usuarioRepository.existsUsuarioByEmail("admin@admin.com");

		if (!exists) {
			Usuario admin = criaAdmin();
			usuarioRepository.save(admin);
		}
	}

	private Usuario criaAdmin() {
		Usuario admin = new Usuario();
		admin.setCpf("08352244906");
		admin.setEmail("admin@admin.com");
		admin.setNomeCompleto("Administrador");
		admin.setSenha(BCrypt.hashpw("&bGWCy^sYg1V", BCrypt.gensalt()));
		admin.setTelefone("48991466677");
		
		Endereco endereco = criaEndereco();
		admin.setEndereco(endereco);
		
		ArrayList<Perfil> perfis = criaRoles();
		admin.setPerfis(perfis);
		return admin;
	}

	private ArrayList<Perfil> criaRoles() {
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		
		Perfil perfil = new Perfil();
		perfil.setNome("ROLE_USER");
		perfis.add(perfil);

		Perfil perfilPrestador = new Perfil();
		perfilPrestador.setNome("ROLE_PRESTADOR");
		perfis.add(perfilPrestador);

		Perfil perfilAdmin = new Perfil();
		perfilAdmin.setNome("ROLE_ADMIN");
		perfis.add(perfilAdmin);
		return perfis;
	}

	private Endereco criaEndereco() {
		Endereco endereco = new Endereco();
		endereco.setBairro("Campeche");
		endereco.setCep("88063160");
		endereco.setLogradouro("Rua das corticeiras");
		endereco.setNumero("114");
		return endereco;
	}
}
