package com.algaworks.algafood.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;

@Profile("dev")
@TipoDeNotificacao(TipoUrgencia.Urgente)
@Component
public class NotificadorEmailMock implements Notificador {

	public NotificadorEmailMock() {
		System.out.println("Notificador mock");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do Mock SMS %s: %s\n", cliente.getNome(), cliente.getTelefone(),
				mensagem);

	}

}
