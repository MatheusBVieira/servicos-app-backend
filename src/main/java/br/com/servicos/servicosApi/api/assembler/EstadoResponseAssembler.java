package br.com.servicos.servicosApi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.EstadoResponse;
import br.com.servicos.servicosApi.domain.model.Estado;

@Component
public class EstadoResponseAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoResponse toResponse(Estado estado) {
		return modelMapper.map(estado, EstadoResponse.class);
	}

	public List<EstadoResponse> toCollectionResponse(List<Estado> estados) {
		return estados.stream().map(estado -> toResponse(estado)).collect(Collectors.toList());
	}
}
