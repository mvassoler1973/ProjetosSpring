package com.algaworks.algafood.model;

public class Cliente {

	private String nome;
	private String email;
	private String telefone;
	private boolean ativo;

	public Cliente() {
		super();
	}

	public Cliente(String nome, String email, String telefone) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public Cliente(String nome, String email, String telefone, boolean ativo) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
