package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal, Long cozinhaId);

	List<Restaurante> findComFreteGratis(String nome);

}