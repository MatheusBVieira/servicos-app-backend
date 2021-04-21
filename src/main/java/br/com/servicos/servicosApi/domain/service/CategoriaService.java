package br.com.servicos.servicosApi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.servicos.servicosApi.domain.exception.CategoriaNaoEncontradaException;
import br.com.servicos.servicosApi.domain.exception.EntidadeEmUsoException;
import br.com.servicos.servicosApi.domain.model.Categoria;
import br.com.servicos.servicosApi.domain.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private static final String MSG_CATEGORIA_EM_USO =  "Categoria de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void excluir(Long categoriaId) {
		try {
			categoriaRepository.deleteById(categoriaId);

		} catch (EmptyResultDataAccessException e) {
			throw new CategoriaNaoEncontradaException(categoriaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CATEGORIA_EM_USO, categoriaId));
		}
		
	}
	
	public Categoria buscarOuFalhar(Long categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));
	}


}
