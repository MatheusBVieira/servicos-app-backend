package br.com.servicos.servicosApi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.servicos.servicosApi.api.model.request.AtualizacaoPrestadorRequest;
import br.com.servicos.servicosApi.api.model.request.PrestadorRequest;
import br.com.servicos.servicosApi.domain.model.PrestadorServico;
import br.com.servicos.servicosApi.domain.model.Usuario;

@Component
public class PrestadorRequestDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PrestadorServico toDomainObject(PrestadorRequest prestadorRequest) {
		return modelMapper.map(prestadorRequest, PrestadorServico.class);
	}
	
	public void copyToDomainObject(AtualizacaoPrestadorRequest usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
