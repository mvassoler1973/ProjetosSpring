package com.br.mvassoler.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.br.mvassoler.food.domain.enumeradores.PedidoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1976054250367367732L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:MM:SS")
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;

	@Column(name = "tipo_pagamento", length = 50, nullable = false)
	private String tipoPagamento;

	@Column(name = "valor_final", precision = 11, scale = 2, nullable = false)
	private BigDecimal valorFinal;

	@Column(name = "valor_promocao", precision = 11, scale = 2)
	private BigDecimal valorPromocao;

	@Enumerated(EnumType.STRING)
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "status", length = 20)
	private PedidoStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Transient
	private List<PedidoItem> pedidoItens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public BigDecimal getValorPromocao() {
		return valorPromocao;
	}

	public void setValorPromocao(BigDecimal valorPromocao) {
		this.valorPromocao = valorPromocao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<PedidoItem> getPedidoItens() {
		return pedidoItens;
	}

	public void setPedidoItens(List<PedidoItem> pedidoItens) {
		this.pedidoItens = pedidoItens;
	}

	public PedidoStatus getStatus() {
		return status;
	}

	public void setStatus(PedidoStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", dataHora=" + dataHora + ", tipoPagamento=" + tipoPagamento + ", valorFinal="
				+ valorFinal + ", cliente=" + cliente + ", pedidoItens=" + pedidoItens + ", getId()=" + getId()
				+ ", getDataHora()=" + getDataHora() + ", getTipoPagamento()=" + getTipoPagamento()
				+ ", getValorFinal()=" + getValorFinal() + ", getCliente()=" + getCliente() + ", getPedidoItens()="
				+ getPedidoItens() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

}
