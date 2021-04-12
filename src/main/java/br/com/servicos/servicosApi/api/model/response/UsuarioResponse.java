package br.com.servicos.servicosApi.api.model.response;

import br.com.servicos.servicosApi.domain.model.Endereco;
import br.com.servicos.servicosApi.domain.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
	
	private Long id;
	private String nomeCompleto;
	private String cpf;
	private String email;
	private String telefone;
	private Endereco endereco;
	
	public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.nomeCompleto = usuario.getNomeCompleto();
		this.cpf = usuario.getCpf();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
		this.endereco = usuario.getEndereco();
	}
}
