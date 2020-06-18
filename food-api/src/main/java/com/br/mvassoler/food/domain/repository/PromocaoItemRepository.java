package com.br.mvassoler.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.PromocaoItem;

@Repository
public interface PromocaoItemRepository extends JpaRepository<PromocaoItem, Long> {

	PromocaoItem findById(long id);

}
