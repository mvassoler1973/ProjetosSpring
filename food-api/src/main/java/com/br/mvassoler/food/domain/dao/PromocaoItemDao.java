package com.br.mvassoler.food.domain.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Item;
import com.br.mvassoler.food.domain.model.PromocaoItem;

@Repository
@Transactional
public class PromocaoItemDao {

	@PersistenceContext
	private EntityManager entityManager;

	public PromocaoItem getPromocaoIngredienteByIngredienteDataFinalValidade(Item item, LocalDate dataFinalValidade) {
		Long id = item.getId();
		String jpql = "from PromocaoItem pro where pro.item.id = :id and pro.dataFinal <= :dataFinalValidade order by pro.valorPromocao desc";
		TypedQuery<PromocaoItem> query = entityManager.createQuery(jpql, PromocaoItem.class);
		query.setParameter("id", id);
		query.setParameter("dataFinalValidade", dataFinalValidade);
		List<PromocaoItem> promocoes = query.getResultList();
		if (promocoes.isEmpty()) {
			return null;
		}
		return promocoes.get(0);

	}

}
