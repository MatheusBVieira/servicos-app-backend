package br.com.servicos.servicosApi.domain.exception;

public class MidiaNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;

	public MidiaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public MidiaNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe um cadastro de mídia com código %d", estadoId));
	}

}
