package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.LiberaAvaliacaoRequest;
import br.com.servicos.servicosApi.domain.model.LiberaAvaliacao;

@Component
public class LiberaAvaliacaoRequestDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public LiberaAvaliacao toDomainObject(LiberaAvaliacaoRequest liberaAvaliacaoRequest) {
		return modelMapper.map(liberaAvaliacaoRequest, LiberaAvaliacao.class);
	}

}

