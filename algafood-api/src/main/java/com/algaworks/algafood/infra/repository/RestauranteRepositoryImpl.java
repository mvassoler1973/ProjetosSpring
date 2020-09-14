package com.algaworks.algafood.infra.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infra.repository.spec.RestauranteSpecs;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		var jpql = new StringBuilder();
		var parametros = new HashMap<String, Object>();

		jpql.append("from Restaurante where 0 = 0 ");
		if (StringUtils.hasLength(nome)) {
			jpql.append(" and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		if (taxaInicial != null) {
			jpql.append(" and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaInicial);
		}
		if (taxaFinal != null) {
			jpql.append(" and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFinal);
		}
		TypedQuery<Restaurante> query = this.manager.createQuery(jpql.toString(), Restaurante.class);
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComNomeTaxaId(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal,
			Long cozinhaId) {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder(); // instância a fábrica de criteria
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class); // equivale from Restaurante
		var predicates = new ArrayList<Predicate>();
		if (StringUtils.hasLength(nome)) {
			// Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if (taxaInicial != null) {
			// Predicate taxaInicialPredicate =
			// builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial);
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		if (taxaFinal != null) {
			// Predicate taxaFinalPredicate =
			// builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal);
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		if (cozinhaId != null) {
			// Predicate taxaFinalPredicate =
			// builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal);
			predicates.add(builder.equal(root.get("cozinha.id"), cozinhaId));
		}
		// criteria.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);
		criteria.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Restaurante> query = this.manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return this.restauranteRepository
				.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}

}
