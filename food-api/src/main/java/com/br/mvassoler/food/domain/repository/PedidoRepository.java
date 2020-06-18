package com.br.mvassoler.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Pedido findById(long id);

}
