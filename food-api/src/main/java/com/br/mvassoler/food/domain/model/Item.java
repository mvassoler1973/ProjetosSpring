package com.br.mvassoler.food.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = 718679703902573146L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;

	@NotBlank
	@Column(name = "tipo_unidade", length = 100, nullable = false)
	private String tipoUnidade;

	@Transient
	private List<ItemIngrediente> itemIngredientes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public List<ItemIngrediente> getItemIngredientes() {
		return itemIngredientes;
	}

	public void setItemIngredientes(List<ItemIngrediente> itemIngredientes) {
		this.itemIngredientes = itemIngredientes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", descricao=" + descricao + ", tipoUnidade=" + tipoUnidade + ", quantidade="
				+ ", precoUnitario=" + ", precoTotal=" + ", valorDesconto=" + ", itemIngredientes=" + "]";
	}

}
