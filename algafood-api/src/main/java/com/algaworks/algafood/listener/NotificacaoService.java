package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoDeNotificacao;
import com.algaworks.algafood.notificacao.TipoUrgencia;
import com.algaworks.algafood.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService {

	@TipoDeNotificacao(TipoUrgencia.Urgente)
	@Autowired
	private Notificador notificador;

	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event) {
		notificador.notificar(event.getCliente(), "Seu cadastro agora esta ativo.");
	}

}
