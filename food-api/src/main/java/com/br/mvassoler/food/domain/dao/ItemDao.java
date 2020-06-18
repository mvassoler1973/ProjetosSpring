package com.br.mvassoler.food.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.ItemIngrediente;

@Repository
@Transactional
public class ItemDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<ItemIngrediente> getAllIngredienteByIdItem(Long id) {
		String jpql = "from ItemIngrediente ing where ing.item.id like :id";
		TypedQuery<ItemIngrediente> query = entityManager.createQuery(jpql, ItemIngrediente.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
