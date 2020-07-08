package com.algaworks.algafood.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;

@Profile("prod")
@TipoDeNotificacao(TipoUrgencia.Urgente)
@Component
public class NotificadorEmail implements Notificador {

	public NotificadorEmail() {
		System.out.println("Notificador email");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}

}