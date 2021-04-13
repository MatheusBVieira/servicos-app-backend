package br.com.servicos.servicosApi.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.UsuarioResponse;
import br.com.servicos.servicosApi.domain.model.Usuario;

@Component
public class UsuarioResponseAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioResponse toResponse(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioResponse.class);
	}

	public List<UsuarioResponse> toCollectionResponse(Collection<Usuario> usuarios) {
		return usuarios.stream().map(usuario -> toResponse(usuario)).collect(Collectors.toList());
	}

}