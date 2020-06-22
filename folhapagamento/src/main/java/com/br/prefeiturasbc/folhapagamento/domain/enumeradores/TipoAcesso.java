package com.br.prefeiturasbc.folhapagamento.domain.enumeradores;

public enum TipoAcesso {
	ADMINISTRADOR(1), SUPERVISOR(2), USUARIO_INTERNO(3), USUARIO_EXTERNO(4);

	private final Integer opcao;

	TipoAcesso(Integer opcao) {
		this.opcao = opcao;
	}

	public Integer getOpcao() {
		return this.opcao;
	}

}
