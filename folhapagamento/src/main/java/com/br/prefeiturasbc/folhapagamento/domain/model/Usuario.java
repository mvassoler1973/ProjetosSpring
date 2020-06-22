package com.br.prefeiturasbc.folhapagamento.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", length = 100, nullable = false)
	@NotBlank
	private String userName;

	@Column(name = "password", length = 1000, nullable = false)
	@NotBlank
	private String password;

	@Column(name = "numero_cpf", length = 14, nullable = false)
	@NotNull
	private Long numeroCpf;

	@Column(name = "data_inicial_sistema", nullable = false)
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	@NotNull
	private LocalDate dataInicialSistema;

	@Column(name = "quantidade_conexao", nullable = true)
	private Integer quantidadeConexao;

	@Column(name = "data_hota_inclusao_registro", nullable = false)
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	@NotNull
	private LocalDateTime dataHoraInclusaoRegistro;

	@Column(name = "id_responsavel_inclusao", nullable = false)
	@NotBlank
	private Long idResponsavelInclusao;

	@Column(name = "data_hota_alteracao_registro", nullable = true)
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private LocalDateTime dataHoraAtualizacaoRegistro;

	@Column(name = "id_responsavel_alteracao", nullable = false)
	private Long idResponsavelAlteracao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_acesso_sistema", nullable = false)
	@Valid
	private GrupoAcessoSistema grupoAcessoSistema;

}
