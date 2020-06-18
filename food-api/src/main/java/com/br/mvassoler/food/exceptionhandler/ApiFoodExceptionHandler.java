package com.br.mvassoler.food.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.mvassoler.food.exceptionhandler.ApiFoodException.Campo;

@ControllerAdvice
public class ApiFoodExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource message;

	@ExceptionHandler(ApiFoodExceptionPadrao.class)
	public ResponseEntity<Object> handleExceptionPadrao(ApiFoodExceptionPadrao ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ApiFoodException excecao = this.setExceptionPadrao(status, ex.getMessage());
		return super.handleExceptionInternal(ex, excecao, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiFoodException excecao = this.setExceptionPadrao(status,
				"Campos inválidos. Revisar os dados informados para a API.");
		List<ApiFoodException.Campo> campos = new ArrayList<ApiFoodException.Campo>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			campos.add(new Campo(((FieldError) error).getField(),
					message.getMessage(error, LocaleContextHolder.getLocale())));
		}
		excecao.setCampos(campos);
		return super.handleExceptionInternal(ex, excecao, headers, status, request);
	}

	private ApiFoodException setExceptionPadrao(HttpStatus status, String mensagem) {
		ApiFoodException excecao = new ApiFoodException();
		excecao.setStatus(status.value());
		excecao.setDescricao(mensagem);
		excecao.setDataHora(LocalDateTime.now());
		return excecao;
	}

}
