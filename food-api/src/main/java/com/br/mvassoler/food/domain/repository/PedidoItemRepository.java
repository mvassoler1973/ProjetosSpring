package com.br.mvassoler.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.PedidoItem;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

	PedidoItem findById(long id);

}
