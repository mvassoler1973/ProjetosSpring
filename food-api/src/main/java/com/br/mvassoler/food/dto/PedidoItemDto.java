package com.br.mvassoler.food.dto;

import java.math.BigDecimal;

public class PedidoItemDto {

	private Long id;
	private BigDecimal quantidade;
	private BigDecimal precounitario;
	private BigDecimal precoTotal;
	private BigDecimal valorDesconto;
	private BigDecimal precoLiquido;
	private PedidoAddDto pedido;
	private ItemDto item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecounitario() {
		return precounitario;
	}

	public void setPrecounitario(BigDecimal precounitario) {
		this.precounitario = precounitario;
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

	public PedidoAddDto getPedido() {
		return pedido;
	}

	public void setPedido(PedidoAddDto pedido) {
		this.pedido = pedido;
	}

	public ItemDto getItem() {
		return item;
	}

	public void setItem(ItemDto item) {
		this.item = item;
	}

}
