package com.br.mvassoler.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Ingrediente;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

	Ingrediente findById(long id);

	Ingrediente findByDescricao(String descricao);

	List<Ingrediente> findByDescricaoContaining(String descricao);
}
