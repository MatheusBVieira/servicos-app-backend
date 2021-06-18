package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.AvaliacaoRequest;
import br.com.servicos.servicosApi.domain.model.Avaliacao;

@Component
public class AvaliacaoRequestDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Avaliacao toDomainObject(AvaliacaoRequest avaliacaoRequest) {
		return modelMapper.map(avaliacaoRequest, Avaliacao.class);
	}
	
	public void copyToDomainObject(AvaliacaoRequest avaliacaoRequest, Avaliacao avaliacao) {
//		avaliacao.setCategoria(new Categoria());
		
		modelMapper.map(avaliacaoRequest, avaliacao);
	}

}
