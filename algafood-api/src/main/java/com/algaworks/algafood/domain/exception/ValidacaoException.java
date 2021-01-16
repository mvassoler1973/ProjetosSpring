package com.algaworks.algafood.domain.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = -7259604909415029318L;

	private BindingResult bindingResult;

}
