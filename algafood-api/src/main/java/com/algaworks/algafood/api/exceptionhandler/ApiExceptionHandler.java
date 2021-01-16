package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_FINAL_USER = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	// Aula 9.11 - Injetado a interface abaixo para capturar as mensagens do arquivo
	// message.properties.
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handlerEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		Problem problema = this
				.createProblemBuilder(HttpStatus.NOT_FOUND, ProblemTypeHandler.ENTIDADE_NAO_ENCONTRADA, ex.getMessage())
				.userMessage(ex.getMessage()).build();
		return this.handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		Problem problema = this
				.createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemTypeHandler.ERRO_NEGOCIO, ex.getMessage())
				.userMessage(ex.getMessage()).build();
		return this.handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		Problem problema = this
				.createProblemBuilder(HttpStatus.CONFLICT, ProblemTypeHandler.ENTIDADE_EM_USO, ex.getMessage())
				.userMessage(ex.getMessage()).build();
		return this.handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		// Importante colocar o printStackTrace (pelo menos por enquanto, que não
		// estamos
		// fazendo logging) para mostrar a stacktrace no console
		// Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam
		// importantes
		// para você durante, especialmente na fase de desenvolvimento
		ex.printStackTrace();
		Problem problem = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ProblemTypeHandler.ERRO_DE_SISTEMA,
				MSG_FINAL_USER).userMessage(MSG_FINAL_USER).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return handlerInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		String detail = "Corpo da requisição está inválida. Verifique erros de sintaxe.";
		Problem problema = this.createProblemBuilder(status, ProblemTypeHandler.MENSAGEM_INCOMPREENSIVEL, detail)
				.userMessage(MSG_FINAL_USER).build();
		return this.handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	private ResponseEntity<Object> handlerInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Proceda a correção para o tipo correto '%s'",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		Problem problema = this.createProblemBuilder(status, ProblemTypeHandler.MENSAGEM_INCOMPREENSIVEL, detail)
				.userMessage(MSG_FINAL_USER).build();
		return this.handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format(
				"A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);
		Problem problema = this.createProblemBuilder(status, ProblemTypeHandler.MENSAGEM_INCOMPREENSIVEL, detail)
				.userMessage(MSG_FINAL_USER).build();
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String detail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		Problem problem = createProblemBuilder(status, ProblemTypeHandler.PARAMETRO_INVALIDO, detail)
				.userMessage(MSG_FINAL_USER).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		Problem problem = createProblemBuilder(status, ProblemTypeHandler.RECURSO_NAO_ENCONTRADO, detail)
				.userMessage(MSG_FINAL_USER).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
					.timestamp(LocalDateTime.now()).userMessage(MSG_FINAL_USER).build();
		} else if (body instanceof String) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
					.timestamp(LocalDateTime.now()).userMessage(MSG_FINAL_USER).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemTypeHandler problemType = ProblemTypeHandler.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		List<Problem.Field> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return Problem.Field.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).fields(problemObjects)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemTypeHandler problemType,
			String detail) {
		return Problem.builder().detail(detail).status(status.value()).type(problemType.getUri())
				.title(problemType.getTitle()).timestamp(LocalDateTime.now());
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
