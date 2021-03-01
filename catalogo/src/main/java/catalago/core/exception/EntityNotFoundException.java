package catalago.core.exception;

/**
 * Exception lançada pelo service quando o entity que esta sendo buscado pelo ID
 * não existe no banco de dados.
 */
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(final String message) {
		super(message);
	}

	public EntityNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
