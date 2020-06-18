package com.br.mvassoler.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.model.Cliente;
import com.br.mvassoler.food.domain.repository.ClienteRepository;

@Service
public class ClienteService implements ServiceGeneric<Cliente, Long> {

	@Autowired
	private ClienteRepository clienteRepository;

	public ClienteRepository getClienteRepository() {
		return clienteRepository;
	}

	@Override
	public Cliente salvar(Cliente entity) {
		return this.getClienteRepository().save(entity);
	}

	@Override
	public Cliente atualizar(Cliente entity) {
		return this.getClienteRepository().save(entity);
	}

	@Override
	public void excluir(Long id) {
		this.getClienteRepository().deleteById(id);
	}

}
