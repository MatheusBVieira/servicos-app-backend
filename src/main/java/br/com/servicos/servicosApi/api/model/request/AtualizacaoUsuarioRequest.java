package br.com.servicos.servicosApi.api.model.request;

import java.lang.reflect.Field;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.ReflectionUtils;

import br.com.servicos.servicosApi.domain.model.Endereco;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.UsuarioRepository;
import lombok.Data;

@Data
public class AtualizacaoUsuarioRequest {

	private String nomeCompleto;
	private String senha;
	private String email;
	private String telefone;
	private EnderecoRequest endereco;;
	
	public Usuario atualizar(Usuario usuarioBanco, UsuarioRepository usuarioRepository) throws Exception {
//		Usuario usuarioBanco = usuarioRepository.getOne(usuario);

		Class<? extends AtualizacaoUsuarioRequest> thisClass = this.getClass();
		Field[] fields = thisClass.getDeclaredFields(); // Pega os campos da classe atual
		
		for (Field field : fields) {
			field.setAccessible(true);
			Object objeto = field.get(this);
			if (objeto != null) { // Verifica quais atributos não estão nulo
				
				Field fieldUsuario = ReflectionUtils.findField(Usuario.class, field.getName()); // Pega o atributo equivalente na classe Usuario
				fieldUsuario.setAccessible(true);
				
				if (field.getName() == "senha") {
					ReflectionUtils.setField(fieldUsuario, usuarioBanco, BCrypt.hashpw(field.get(this).toString(), BCrypt.gensalt()));
				}
				if (field.getName() == "endereco") {
					Endereco enderecoReal = endereco.converter();
					ReflectionUtils.setField(fieldUsuario, usuarioBanco, enderecoReal);
				}
				else {
					ReflectionUtils.setField(fieldUsuario, usuarioBanco, field.get(this)); // Troca o valor do atributo da classe usuario pelo valor novo
				}
				
				
				
			}
		}
		Usuario usuarioAtualizado = usuarioRepository.save(usuarioBanco);
		return usuarioAtualizado;
	}

}
