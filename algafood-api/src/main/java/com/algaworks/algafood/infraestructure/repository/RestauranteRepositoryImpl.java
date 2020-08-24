package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		TypedQuery<Restaurante> query = this.manager.createQuery("from Restaurante", Restaurante.class);
		return query.getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return this.manager.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return this.manager.merge(restaurante);
	}

	@Override
	public void remover(Long id) {
		Restaurante restaurante = this.buscar(id);
		if (restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		this.manager.remove(restaurante);

	}

}
