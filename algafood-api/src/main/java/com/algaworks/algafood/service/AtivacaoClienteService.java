package com.algaworks.algafood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired
	private List<Notificador> notificares;

	public void ativar(Cliente cliente) {
		cliente.setAtivo(true);
		for (Notificador notificar : this.notificares) {
			notificar.notificar(cliente, "Validando e-mail do seu cadastro");
		}
	}

}
