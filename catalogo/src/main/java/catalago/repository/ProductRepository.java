package catalago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catalago.model.Product;
import catalago.repository.query.ProductQuery;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQuery {

}
