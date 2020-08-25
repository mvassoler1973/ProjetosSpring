package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Estado> listar() {
		TypedQuery<Estado> query = this.manager.createQuery("from Estado", Estado.class);
		return query.getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		return this.manager.find(Estado.class, id);
	}

	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return this.manager.merge(estado);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Estado estado = this.buscar(id);
		if (estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		this.manager.remove(estado);

	}

}
