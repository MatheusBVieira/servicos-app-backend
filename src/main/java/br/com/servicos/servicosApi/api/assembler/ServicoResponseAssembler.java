package br.com.servicos.servicosApi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.ServicoResponse;
import br.com.servicos.servicosApi.domain.model.Servico;

@Component
public class ServicoResponseAssembler {
	@Autowired
	private ModelMapper modelMapper;

	public ServicoResponse toResponse(Servico servico) {
		return modelMapper.map(servico, ServicoResponse.class);
	}

	public List<ServicoResponse> toCollectionResponse(List<Servico> servicos) {
		return servicos.stream().map(servico -> toResponse(servico)).collect(Collectors.toList());
	}
}
