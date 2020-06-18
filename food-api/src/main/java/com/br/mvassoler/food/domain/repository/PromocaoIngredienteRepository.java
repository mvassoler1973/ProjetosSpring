package com.br.mvassoler.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.PromocaoIngrediente;

@Repository
public interface PromocaoIngredienteRepository extends JpaRepository<PromocaoIngrediente, Long> {

	PromocaoIngrediente findById(long id);

}
