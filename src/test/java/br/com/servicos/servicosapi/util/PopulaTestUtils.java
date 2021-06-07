package br.com.servicos.servicosapi.util;

import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.model.Cidade;
import br.com.servicos.servicosApi.domain.model.Estado;

public class PopulaTestUtils {

	public static Estado criaEstadoSC() {
		Estado estadoSC = new Estado();
		estadoSC.setNome("Santa Catarina");
		return estadoSC;
	}
	
	public static Cidade criaCidadeFloripa(Estado estado) {
		Cidade cidadeFloripa = new Cidade();
		cidadeFloripa.setEstado(estado);
		cidadeFloripa.setNome("Florianópolis");
		return cidadeFloripa;
	}

	public static Categoria criaCategoriaLimpeza() {
		Categoria categoriaLimpeza = new Categoria();
		categoriaLimpeza.setCategoria("Limpeza");
		return categoriaLimpeza;
	}
	
}
