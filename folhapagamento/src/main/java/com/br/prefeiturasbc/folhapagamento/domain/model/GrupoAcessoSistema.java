package com.br.prefeiturasbc.folhapagamento.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.br.prefeiturasbc.folhapagamento.domain.conversor.TipoAcessoConverter;
import com.br.prefeiturasbc.folhapagamento.domain.enumeradores.TipoAcesso;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "grupo_acesso_sistema")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GrupoAcessoSistema implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao", length = 100, nullable = false)
	@NotBlank
	private String descricao;

	@Column(name = "tipo_acesso", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	@Convert(converter = TipoAcessoConverter.class)
	@NotNull
	private TipoAcesso tipoAcesso;

}
