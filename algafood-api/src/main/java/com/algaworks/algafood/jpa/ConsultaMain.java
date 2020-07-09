package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class ConsultaMain {

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		ConsultaCozinha consulta = context.getBean(ConsultaCozinha.class);
		List<Cozinha> cozinhas = consulta.listar();
		cozinhas.forEach(c -> System.out.println(c.getId() + " " + c.getNome()));

	}

}
