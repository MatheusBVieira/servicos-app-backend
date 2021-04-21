package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.CategoriaRequest;
import br.com.servicos.servicosApi.domain.model.Categoria;

@Component
public class CategoriaRequestDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Categoria toDomainObject(CategoriaRequest categoriaRequest) {
		return modelMapper.map(categoriaRequest, Categoria.class);
	}

	public void copyToDomainObject(CategoriaRequest categoriaRequest, Categoria categoria) {
		modelMapper.map(categoriaRequest, categoria);
	}
}
