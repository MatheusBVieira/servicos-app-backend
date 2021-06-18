package br.com.servicos.servicosApi.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.servicos.servicosApi.api.assembler.AvaliacaoRequestDisassembler;
import br.com.servicos.servicosApi.api.assembler.AvaliacaoResponseAssembler;
import br.com.servicos.servicosApi.api.assembler.LiberaAvaliacaoRequestDisassembler;
import br.com.servicos.servicosApi.api.model.request.AvaliacaoRequest;
import br.com.servicos.servicosApi.api.model.request.LiberaAvaliacaoRequest;
import br.com.servicos.servicosApi.api.model.response.AvaliacaoResponse;
import br.com.servicos.servicosApi.api.model.response.MediaResponse;
import br.com.servicos.servicosApi.api.openapi.controller.AvaliacaoControllerOpenApi;
import br.com.servicos.servicosApi.domain.model.Avaliacao;
import br.com.servicos.servicosApi.domain.model.LiberaAvaliacao;
import br.com.servicos.servicosApi.domain.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController implements AvaliacaoControllerOpenApi {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoResponseAssembler avaliacaoResponseAssembler;

	@Autowired
	private AvaliacaoRequestDisassembler avaliacaoRequestDisassembler;

	@Autowired
	private LiberaAvaliacaoRequestDisassembler liberaAvaliacaoRequestDisassembler;

	@GetMapping
	public List<AvaliacaoResponse> listar(@RequestParam(required = true) Long servicoId,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 20) Pageable paginacao) {

		Page<Avaliacao> avaliacaoes = avaliacaoService.listaPorServicoId(paginacao, servicoId);

		return avaliacaoResponseAssembler.toCollectionResponse(avaliacaoes.getContent());
	}

	@GetMapping("/{avaliacaoId}")
	public AvaliacaoResponse busca(@PathVariable Long avaliacaoId) {
		Avaliacao avaliacao = avaliacaoService.buscarOuFalhar(avaliacaoId);
		return avaliacaoResponseAssembler.toResponse(avaliacao);
	}

	@GetMapping("/libera")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void busca(@RequestParam(required = true) Long servicoId,
			HttpServletRequest request) {
		
		avaliacaoService.podeComentar(servicoId, request);
		
	}

	@GetMapping("/media")
	public MediaResponse retornaNotaMedia(@RequestParam(required = true) Long servicoId) {
		return avaliacaoService.retornaMedia(servicoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping("/{servicoId}")
	public AvaliacaoResponse adicionar(@PathVariable Long servicoId,
			@RequestBody @Valid AvaliacaoRequest avaliacaoRequest, HttpServletRequest request) {

		Avaliacao avaliacao = avaliacaoRequestDisassembler.toDomainObject(avaliacaoRequest);

		avaliacao = avaliacaoService.salvar(avaliacao, request, servicoId);

		return avaliacaoResponseAssembler.toResponse(avaliacao);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping("/libera")
	public void adicionar(@RequestBody @Valid LiberaAvaliacaoRequest liberaAvaliacaoRequest,
			HttpServletRequest request) {
		LiberaAvaliacao liberaAvaliacao = liberaAvaliacaoRequestDisassembler.toDomainObject(liberaAvaliacaoRequest);

		avaliacaoService.salvar(liberaAvaliacao, request);
	}

	@DeleteMapping("/{avaliacaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long avaliacaoId) {
		avaliacaoService.excluir(avaliacaoId);
	}
}
