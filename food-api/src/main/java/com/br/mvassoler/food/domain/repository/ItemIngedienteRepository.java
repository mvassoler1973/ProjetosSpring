package com.br.mvassoler.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.ItemIngrediente;

@Repository
public interface ItemIngedienteRepository extends JpaRepository<ItemIngrediente, Long> {

	ItemIngrediente findById(long id);

}
