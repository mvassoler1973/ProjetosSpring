package com.br.mvassoler.food.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 4955200457945702794L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@NotBlank
	@Column(name = "endereco", length = 100, nullable = false)
	private String endereco;

	@Column(name = "numero", length = 20, nullable = true)
	private String numero;

	@NotNull
	@Column(name = "cep", length = 8, nullable = false)
	private Integer cep;

	@Column(name = "ddd_telefone", length = 3, nullable = true)
	private Integer dddTelefone;

	@Column(name = "numero_telefone", length = 9, nullable = true)
	private Integer numeroTelefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCEP(Integer cep) {
		this.cep = cep;
	}

	public Integer getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(Integer dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public Integer getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(Integer numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", numero=" + numero + ", CEP=" + cep
				+ ", dddTelefone=" + dddTelefone + ", numeroTelefone=" + numeroTelefone + "]";
	}

}
