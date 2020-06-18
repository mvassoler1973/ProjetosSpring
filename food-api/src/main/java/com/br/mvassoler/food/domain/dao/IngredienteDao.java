package com.br.mvassoler.food.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Ingrediente;

@Repository
@Transactional
public class IngredienteDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Ingrediente> getByDescricao(String descricao) {
		String jpql = "from Ingrediente ing where ing.descricao like :descricao";
		TypedQuery<Ingrediente> query = entityManager.createQuery(jpql, Ingrediente.class);
		query.setParameter("descricao", "%" + descricao + "%");
		return query.getResultList();
	}

}
