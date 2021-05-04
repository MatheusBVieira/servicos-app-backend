package br.com.servicos.servicosApi.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.PrestadorResponse;
import br.com.servicos.servicosApi.domain.model.PrestadorServico;

@Component
public class PrestadorResponseAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PrestadorResponse toResponse(PrestadorServico prestador) {
		return modelMapper.map(prestador, PrestadorResponse.class);
	}

	public List<PrestadorResponse> toCollectionResponse(Collection<PrestadorServico> prestadores) {
		return prestadores.stream().map(prestador -> toResponse(prestador)).collect(Collectors.toList());
	}
	
}
