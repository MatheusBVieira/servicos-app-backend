package br.com.servicos.servicosApi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.CategoriaResponse;
import br.com.servicos.servicosApi.domain.model.Categoria;

@Component
public class CategoriaResponseAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CategoriaResponse toResponse(Categoria categoria) {
		return modelMapper.map(categoria, CategoriaResponse.class);
	}

	public List<CategoriaResponse> toCollectionResponse(List<Categoria> categorias) {
		return categorias.stream().map(categoria -> toResponse(categoria)).collect(Collectors.toList());
	}
	
}
