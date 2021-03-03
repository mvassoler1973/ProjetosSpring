package catalago.core.exception;

public class ProductsNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de produto para o ID %d";

	public ProductsNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public ProductsNaoEncontradaException(Long restauranteId) {
		this(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId));
	}

}
