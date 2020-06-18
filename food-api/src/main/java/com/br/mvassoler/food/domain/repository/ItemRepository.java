package com.br.mvassoler.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Item findById(long id);

	Item findByDescricao(String descricao);

	List<Item> findByDescricaoContaining(String descricao);
}
