package com.algaworks.algafood.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoDeNotificacao;
import com.algaworks.algafood.notificacao.TipoUrgencia;

@Component
public class AtivacaoClienteService {

	@TipoDeNotificacao(TipoUrgencia.Normal)
	@Autowired
	private Notificador notificar;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@PostConstruct
	public void init() {
		System.out.println("Init");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Destroy");
	}

	public void ativar(Cliente cliente) {
		cliente.setAtivo(true);

		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
		// notificar.notificar(cliente, "Validando e-mail do seu cadastro");

	}

}
