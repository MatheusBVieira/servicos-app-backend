package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.AtualizacaoUsuarioRequest;
import br.com.servicos.servicosApi.api.model.request.UsuarioRequest;
import br.com.servicos.servicosApi.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioRequest usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(AtualizacaoUsuarioRequest usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
}
