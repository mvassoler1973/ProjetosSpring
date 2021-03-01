package catalago.core.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;

import catalago.core.constants.ValidationConstants;
import catalago.core.dto.ErrorDetailsDTO;
import catalago.core.exception.BadRequestException;
import catalago.core.exception.ForbidenException;
import catalago.core.locale.MessageLocaleComponent;
import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public abstract class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

	protected final MessageLocaleComponent messageLocale;

	protected GlobalEntityExceptionHandler(final MessageLocaleComponent messageLocale) {
		this.messageLocale = messageLocale;
	}

	/**
	 * Handler para tratar BadRequestException, lançada pelos serviços.
	 *
	 * @param ex a exception
	 * @return ResponseEntity como 400 e contendo mensagem no corpo do response.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<List<ErrorDetailsDTO>> badRequestException(final BadRequestException ex) {
		log.info("M=BadRequestException", ex);

		String exception = ClassUtils.getShortClassName(ex.getClass());
		ErrorDetailsDTO error = ErrorDetailsDTO.builder().code(400).exception(exception).statusCode(BAD_REQUEST)
				.message(ex.getMessage()).build();
		return ResponseEntity.status(BAD_REQUEST).body(Arrays.asList(error));
	}

	/**
	 * Handler para tratar ForbidenException, lançada pelos serviços.
	 *
	 * @param ex a exception
	 * @return ResponseEntity como 403 e contendo mensagem no corpo do response.
	 */
	@ExceptionHandler(ForbidenException.class)
	public ResponseEntity<List<ErrorDetailsDTO>> forbidenException(final ForbidenException ex) {
		log.info("M=ForbidenException", ex);

		String exception = ClassUtils.getShortClassName(ex.getClass());
		ErrorDetailsDTO error = ErrorDetailsDTO.builder().code(403).exception(exception).statusCode(FORBIDDEN)
				.message(ex.getMessage()).build();
		return ResponseEntity.status(FORBIDDEN).body(Arrays.asList(error));
	}

	/**
	 * Handler para tratar EntityNotFoundException, lançada pelo CrudService se o id
	 * passado no find não existe.
	 *
	 * @param ex a exception
	 * @return ResponseEntity como 404 e contendo mensagem no corpo do response.
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<List<ErrorDetailsDTO>> entityNotFoundException(final EntityNotFoundException ex) {
		log.info("M=EntityNotFoundException", ex);

		String exception = ClassUtils.getShortClassName(ex.getClass());
		ErrorDetailsDTO error = ErrorDetailsDTO.builder().code(404).exception(exception).statusCode(NOT_FOUND)
				.message(ex.getMessage()).build();
		return ResponseEntity.status(NOT_FOUND).body(Arrays.asList(error));
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info("M=handleBindException", ex);
		return getListErros(ex.getBindingResult(), ClassUtils.getShortClassName(ex.getClass()), ex);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info("M=handleMethodArgumentNotValid", ex.getMessage());
		return getListErros(ex.getBindingResult(), ClassUtils.getShortClassName(ex.getClass()), ex);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info("M=handleHttpMessageNotReadable", ex);
		List<ErrorDetailsDTO> errors = new ArrayList<>();
		Throwable rootThowable = ex.getCause();
		((JsonMappingException) rootThowable).getPath().forEach(e -> {
			String exception = ClassUtils.getShortClassName(ex.getClass());
			errors.add(new ErrorDetailsDTO(e.getFieldName(), exception,
					messageLocale.validationMessageSource(ValidationConstants.FIELD_INVALID_VALUE), BAD_REQUEST,
					BAD_REQUEST.value()));
		});
		return ResponseEntity.status(BAD_REQUEST).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info("M=handleMissingServletRequestParameter", ex);
		String exception = ClassUtils.getShortClassName(ex.getClass());
		ErrorDetailsDTO error = new ErrorDetailsDTO(ex.getParameterName(), exception,
				messageLocale.validationMessageSource(ValidationConstants.FIELD_INVALID_VALUE), BAD_REQUEST,
				BAD_REQUEST.value());
		return ResponseEntity.status(BAD_REQUEST).body(Arrays.asList(error));
	}

	private ResponseEntity<Object> getListErros(final BindingResult bindingResult, final String shortClassName,
			final Exception ex) {
		List<ErrorDetailsDTO> errors = new ArrayList<>();
		bindingResult.getFieldErrors().forEach(error -> {
			String exception = shortClassName;
			errors.add(new ErrorDetailsDTO(error.getField(), exception, error.getDefaultMessage(), BAD_REQUEST,
					BAD_REQUEST.value()));
			log.error(ex.getMessage());
		});
		return ResponseEntity.status(BAD_REQUEST).body(errors);
	}
}