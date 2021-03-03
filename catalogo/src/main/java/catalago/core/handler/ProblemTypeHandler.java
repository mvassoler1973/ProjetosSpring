package catalago.core.handler;

import lombok.Getter;

@Getter
public enum ProblemTypeHandler {

	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade Não Encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensível"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	RANGE_FILTER_INVALID("/dados-invalidos", "Valores do range inválido"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

	private String title;
	private String uri;

	private ProblemTypeHandler(String path, String title) {
		this.uri = "http://mvassoler.com.br" + path;
		this.title = title;
	}

}
