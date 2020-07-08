package com.algaworks.algafood.notificacao;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;

@TipoDeNotificacao(TipoUrgencia.Normal)
@Component
public class NotificadorSms implements Notificador {

	public NotificadorSms() {
		System.out.println("Notificador sms");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do SMS %s: %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);

	}

}
