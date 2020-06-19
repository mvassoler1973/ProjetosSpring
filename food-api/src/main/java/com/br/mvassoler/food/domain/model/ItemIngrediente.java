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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item_ingrediente")
public class ItemIngrediente implements Serializable {

	private static final long serialVersionUID = -8942572747529247257L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantidade", precision = 9, scale = 3, nullable = false)
	@NotNull
	private BigDecimal quantidade;

	@Column(name = "preco_unitario", precision = 11, scale = 2, nullable = false)
	@NotNull
	private BigDecimal precoUnitario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ingrediente", nullable = false)
	private Ingrediente ingrediente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_item", nullable = false)
	@JsonIgnore
	private Item item;

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

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
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
		ItemIngrediente other = (ItemIngrediente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemIngrediente [id=" + id + ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario
				+ ", ingrediente=" + ingrediente + ", item=" + item + "]";
	}

}
