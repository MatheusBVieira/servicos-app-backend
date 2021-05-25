package br.com.servicos.servicosApi.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.servicos.servicosApi.domain.model.Midia;
import br.com.servicos.servicosApi.domain.service.MidiaService;

@RestController
@RequestMapping("/midia")
public class MidiaController {

	@Autowired
	private MidiaService midiaService;

	@GetMapping("/{idMidia}")
	public ResponseEntity<Resource> exibe(@PathVariable Long idMidia) {
		Midia midia = midiaService.buscarOuFalar(idMidia);
		return retornoMidia(midia);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "multipart/form-data")
	public ResponseEntity<Resource> cadastrar(@RequestParam("midia") MultipartFile file,
			HttpServletRequest request,
			UriComponentsBuilder uriBuilder) {

		Midia midia = midiaService.insere(file, request);
		return retornoMidia(midia);
	}

	private ResponseEntity<Resource> retornoMidia(Midia midia) {
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(midia.getTipo().toString()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + midia.getNome() + "\"")
				.body(new ByteArrayResource(midia.getMidia()));
	}
}
