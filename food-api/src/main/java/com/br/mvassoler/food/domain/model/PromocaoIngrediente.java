package com.br.mvassoler.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "promocao_ingrediente")
public class PromocaoIngrediente implements Serializable {

	private static final long serialVersionUID = -688557075855052952L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantidade", precision = 9, scale = 3, nullable = false)
	private BigDecimal quantidade;

	@Column(name = "valor_promocao", precision = 11, scale = 2, nullable = false)
	private BigDecimal valorPromocao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "data_inicial", nullable = false)
	private LocalDate dataInicial;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "data_final", nullable = false)
	private LocalDate dataFinal;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ingrediente", nullable = false)
	private Ingrediente ingrediente;

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

	public BigDecimal getValorPromocao() {
		return valorPromocao;
	}

	public void setValorPromocao(BigDecimal valorPromocao) {
		this.valorPromocao = valorPromocao;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
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
		PromocaoIngrediente other = (PromocaoIngrediente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PromocaoIngrediente [id=" + id + ", quantidade=" + quantidade + ", valorPromocao=" + valorPromocao
				+ ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", ingrediente=" + ingrediente
				+ ", getId()=" + getId() + ", getQuantidade()=" + getQuantidade() + ", getValorPromocao()="
				+ getValorPromocao() + ", getDataInicial()=" + getDataInicial() + ", getDataFinal()=" + getDataFinal()
				+ ", getIngrediente()=" + getIngrediente() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

}
