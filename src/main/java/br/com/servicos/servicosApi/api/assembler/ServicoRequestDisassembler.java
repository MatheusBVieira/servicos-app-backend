package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.ServicoRequest;
import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Servico;

@Component
public class ServicoRequestDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Servico toDomainObject(ServicoRequest servicoRequest) {
		return modelMapper.map(servicoRequest, Servico.class);
	}
	
	public void copyToDomainObject(ServicoRequest servicoRequest, Servico servico) {
		servico.setCategoria(new Categoria());
		
		modelMapper.map(servicoRequest, servico);
	}
}
