package br.com.servicos.servicosApi.domain.util;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCrypt;

import br.com.servicos.servicosApi.domain.model.Endereco;
import br.com.servicos.servicosApi.domain.model.Perfil;
import br.com.servicos.servicosApi.domain.model.Usuario;

public class UsuarioUtil {
	
	public static Usuario criaAdmin(String senha) {
		Usuario admin = new Usuario();
		admin.setCpf("08352244906");
		admin.setEmail("admin@admin.com");
		admin.setNomeCompleto("Administrador");
		admin.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
		admin.setTelefone("48991466677");
		
		Endereco endereco = criaEndereco();
		admin.setEndereco(endereco);
		
		ArrayList<Perfil> perfis = criaRoles();
		admin.setPerfis(perfis);
		return admin;
	}

	private static ArrayList<Perfil> criaRoles() {
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

	private static Endereco criaEndereco() {
		Endereco endereco = new Endereco();
		endereco.setBairro("Campeche");
		endereco.setCep("88063160");
		endereco.setLogradouro("Rua das corticeiras");
		endereco.setNumero("114");
		return endereco;
	}
}
