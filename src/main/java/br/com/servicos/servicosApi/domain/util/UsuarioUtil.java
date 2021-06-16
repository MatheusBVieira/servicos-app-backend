package br.com.servicos.servicosApi.domain.util;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCrypt;

import br.com.servicos.servicosApi.domain.model.Endereco;
import br.com.servicos.servicosApi.domain.model.Perfil;
import br.com.servicos.servicosApi.domain.model.Usuario;

public class UsuarioUtil {
	
	public static Usuario criaPrestador(String senha) {
		Usuario prestador = new Usuario();
		prestador.setCpf("72250167095");
		prestador.setEmail("matheusbvieira@hotmail.com");
		prestador.setNomeCompleto("Matheus Bruggemann Vieira");
		prestador.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
		prestador.setTelefone("48991466677");
		
		Endereco endereco = criaEndereco(-27.596589, -48.546171);
		prestador.setEndereco(endereco);
		
		ArrayList<Perfil> perfis = criaRoles(false);
		prestador.setPerfis(perfis);
		return prestador;
	}
	
	public static Usuario criaAdmin(String senha) {
		Usuario admin = new Usuario();
		admin.setCpf("08352244906");
		admin.setEmail("admin@admin.com");
		admin.setNomeCompleto("Administrador");
		admin.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
		admin.setTelefone("48991466677");
		
		Endereco endereco = criaEndereco(-27.68909167405744, -48.48507045958693);
		admin.setEndereco(endereco);
		
		ArrayList<Perfil> perfis = criaRoles(true);
		admin.setPerfis(perfis);
		return admin;
	}

	private static ArrayList<Perfil> criaRoles(boolean isAdmin) {
		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		
		Perfil perfil = new Perfil();
		perfil.setNome("ROLE_USER");
		perfis.add(perfil);

		Perfil perfilPrestador = new Perfil();
		perfilPrestador.setNome("ROLE_PRESTADOR");
		perfis.add(perfilPrestador);
		
		if (isAdmin) {
			Perfil perfilAdmin = new Perfil();
			perfilAdmin.setNome("ROLE_ADMIN");
			perfis.add(perfilAdmin);
		}
		
		return perfis;
	}

	private static Endereco criaEndereco(Double latitude, Double longitude) {
		Endereco endereco = new Endereco();
		endereco.setBairro("Campeche");
		endereco.setCep("88063160");
		endereco.setLogradouro("Rua das corticeiras");
		endereco.setNumero("114");
		endereco.setLatitude(latitude);
		endereco.setLongitude(longitude);
		return endereco;
	}

}
