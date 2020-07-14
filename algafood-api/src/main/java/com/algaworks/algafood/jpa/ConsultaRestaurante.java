package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaRestaurante {

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		RestauranteRepository consulta = context.getBean(RestauranteRepository.class);
		List<Restaurante> restaurantes = consulta.listar();
		restaurantes.forEach(r -> System.out.println(
				"Id = " + r.getId() + " - Nome = " + r.getNome() + " - Cozinha = " + r.getCozinha().getNome()));
	}

}
