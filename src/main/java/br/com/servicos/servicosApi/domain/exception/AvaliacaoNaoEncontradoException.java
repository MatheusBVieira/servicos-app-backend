package br.com.servicos.servicosApi.domain.exception;

public class AvaliacaoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public AvaliacaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public AvaliacaoNaoEncontradoException(Long usuarioId) {
		this(String.format("Não existe um cadastro de avaliação com código %d", usuarioId));
	}
}
