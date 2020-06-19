package com.br.mvassoler.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pedido_item")
public class PedidoItem implements Serializable {

	private static final long serialVersionUID = 7116041487413336323L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "preco_total", precision = 11, scale = 2, nullable = false)
	private BigDecimal precoTotal;

	@Column(name = "valor_desconto", precision = 11, scale = 2, nullable = false)
	private BigDecimal valorDesconto;

	@Column(name = "preco_liquido", precision = 11, scale = 2, nullable = false)
	private BigDecimal precoLiquido;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pedido", nullable = false)
	@JsonIgnore
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_item", nullable = false)
	// @JsonIgnore
	private Item item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getPrecoLiquido() {
		return precoLiquido;
	}

	public void setPrecoLiquido(BigDecimal precoLiquido) {
		this.precoLiquido = precoLiquido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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
		PedidoItem other = (PedidoItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedidoItem [id=" + id + ", precoTotal=" + precoTotal + ", valorDesconto=" + valorDesconto
				+ ", precoLiquido=" + precoLiquido + ", pedido=" + pedido + ", item=" + item + "]";
	}

}
