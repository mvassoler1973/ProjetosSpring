package catalago.repository.query;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import catalago.model.Product;

public interface ProductQuery {

	Page<Product> findAllByFilter(String name, String description, BigDecimal lowestPrice, BigDecimal bigPrice,
			Pageable pageable, Integer pagina);

}
