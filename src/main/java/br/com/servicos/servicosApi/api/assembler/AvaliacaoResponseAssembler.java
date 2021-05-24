package br.com.servicos.servicosApi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.response.AvaliacaoResponse;
import br.com.servicos.servicosApi.domain.model.Avaliacao;

@Component
public class AvaliacaoResponseAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public AvaliacaoResponse toResponse(Avaliacao avaliacao) {
		return modelMapper.map(avaliacao, AvaliacaoResponse.class);
	}

	public List<AvaliacaoResponse> toCollectionResponse(List<Avaliacao> avaliacoes) {
		return avaliacoes.stream().map(avaliacao -> toResponse(avaliacao)).collect(Collectors.toList());
	}
}
