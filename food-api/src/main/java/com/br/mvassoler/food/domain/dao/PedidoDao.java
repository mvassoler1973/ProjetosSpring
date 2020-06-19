package com.br.mvassoler.food.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.PedidoItem;

@Repository
@Transactional
public class PedidoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<PedidoItem> getAllIItemByIdPedido(Long id) {
		String jpql = "from PedidoItem ite where ite.pedido.id = :id";
		TypedQuery<PedidoItem> query = entityManager.createQuery(jpql, PedidoItem.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
