package br.com.servicos.servicosApi.domain.service;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.servicos.servicosApi.domain.exception.MidiaNaoEncontradaException;
import br.com.servicos.servicosApi.domain.exception.NegocioException;
import br.com.servicos.servicosApi.domain.model.Midia;
import br.com.servicos.servicosApi.domain.model.Usuario;
import br.com.servicos.servicosApi.domain.repository.MidiaRepository;

@Service
public class MidiaService {

	private static final String MSG_ERRO_IMAGEM = "A imagem enviada não pode ser aceita";

	@Autowired
	private MidiaRepository midiaRepository;

	@Autowired
	private UsuarioService usuarioService;

	public Midia buscarOuFalar(Long idMidia) {
		return midiaRepository.findById(idMidia).orElseThrow(() -> new MidiaNaoEncontradaException(idMidia));
	}
	
	public Midia insere(@Valid MultipartFile midiaFile, HttpServletRequest request) {
		Usuario usuario = usuarioService.getOne(request);
		if (usuario.getMidiaPath() != null) {
			String[] midiaPath = usuario.getMidiaPath().split("/");
			String idImagemAntiga = midiaPath[1];
			midiaRepository.deleteById(Long.parseLong(idImagemAntiga));
		}

		try {
			Midia midia = criaMidia(midiaFile);

			midia = midiaRepository.save(midia);

			usuarioService.insereMidia(midia.getId(), request);

			return midia;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(MSG_ERRO_IMAGEM);
		}
	}

	private Midia criaMidia(MultipartFile file) throws IOException {

		byte[] bytes = file.getBytes();
		Midia midia = new Midia();

		midia.setMidia(bytes);

		File arquivo = new File(file.getOriginalFilename());
		midia.setNome(arquivo.getName());

		String tipo = URLConnection.getFileNameMap().getContentTypeFor(arquivo.getName());
		midia.setTipo(tipo);

		midia.setDataDeUpload(LocalDate.now());

		return midia;
	}

	

}
