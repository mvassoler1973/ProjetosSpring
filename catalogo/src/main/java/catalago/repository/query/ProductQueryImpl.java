package catalago.repository.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import catalago.model.Product;

@Repository
public class ProductQueryImpl implements ProductQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Product> findAllByFilter(String name, String description, BigDecimal lowestPrice, BigDecimal bigPrice,
			Pageable pageable, Integer pagina) {
		Long total = total(name, description, lowestPrice, bigPrice);

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> root = criteria.from(Product.class);

		Predicate[] predicates = setPredicateByDescriptionDateImportDateConcludedStatus(name, description, lowestPrice,
				bigPrice, builder, root);
		criteria.where(predicates);
		TypedQuery<Product> query = manager.createQuery(criteria);

		List<Product> products;
		if (pagina == null) {
			products = query.getResultList();
		} else {
			query.setFirstResult(pagina * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			products = query.getResultList();
		}

		return new PageImpl<>(products, pageable, total);
	}

	private Predicate[] setPredicateByDescriptionDateImportDateConcludedStatus(String name, String description,
			BigDecimal lowestPrice, BigDecimal bigPrice, CriteriaBuilder builder, Root<Product> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (name != null && !name.isEmpty()) {
			predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (description != null && !description.isEmpty()) {
			predicates.add(builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
		}

		if (lowestPrice != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("price"), lowestPrice));
		}
		if (bigPrice != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("price"), bigPrice));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long total(String name, String description, BigDecimal lowestPrice, BigDecimal bigPrice) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Product> root = criteria.from(Product.class);

		Predicate[] predicates = setPredicateByDescriptionDateImportDateConcludedStatus(name, description, lowestPrice,
				bigPrice, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
