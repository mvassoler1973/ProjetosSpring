package com.br.mvassoler.food.domain.service;

public interface ServiceGeneric<T, K> {

	T salvar(T entity);

	T atualizar(T entity);

	void excluir(K id);

}
