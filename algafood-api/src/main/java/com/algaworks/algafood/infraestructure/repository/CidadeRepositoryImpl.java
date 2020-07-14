package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar() {
		TypedQuery<Cidade> query = this.manager.createQuery("from Cidade", Cidade.class);
		return query.getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return this.manager.find(Cidade.class, id);
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return this.manager.merge(cidade);
	}

	@Override
	@Transactional
	public void remover(Cidade cidade) {
		cidade = this.buscar(cidade.getId());
		this.manager.remove(cidade);
	}

}
