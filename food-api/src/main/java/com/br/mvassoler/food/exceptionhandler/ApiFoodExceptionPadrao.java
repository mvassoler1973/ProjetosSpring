package com.br.mvassoler.food.exceptionhandler;

public class ApiFoodExceptionPadrao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiFoodExceptionPadrao(String mensagem) {
		super(mensagem);
	}

}
