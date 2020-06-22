package com.br.prefeiturasbc.folhapagamento.domain.conversor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.br.prefeiturasbc.folhapagamento.domain.enumeradores.TipoAcesso;

@Converter(autoApply = true)
public class TipoAcessoConverter implements AttributeConverter<TipoAcesso, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TipoAcesso tipoAcesso) {
		return tipoAcesso.getOpcao();
	}

	@Override
	public TipoAcesso convertToEntityAttribute(Integer dbData) {
		for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
			if (tipoAcesso.getOpcao().equals(dbData)) {
				return tipoAcesso;
			}
		}
		throw new IllegalArgumentException("Opcao do tipo de acesso inválida:" + dbData);
	}

}
