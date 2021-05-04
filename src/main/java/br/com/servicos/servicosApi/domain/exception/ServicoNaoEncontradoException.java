package br.com.servicos.servicosApi.domain.exception;

public class ServicoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ServicoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ServicoNaoEncontradoException(Long usuarioId) {
		this(String.format("Não existe um cadastro de servico com código %d", usuarioId));
	}
}
