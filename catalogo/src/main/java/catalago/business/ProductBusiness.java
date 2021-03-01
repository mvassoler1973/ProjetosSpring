package catalago.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import catalago.core.constants.ValidationConstants;
import catalago.core.exception.EntityNotFoundException;
import catalago.core.locale.MessageLocale;
import catalago.dto.ProductDTO;
import catalago.mapper.ProductMapper;
import catalago.model.Product;
import catalago.repository.ProductRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Data
public class ProductBusiness {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductMapper mapper;

	@Autowired
	private MessageLocale message;

	public Product getProductById(Long id) {
		try {
			return this.repository.findById(id).get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new EntityNotFoundException(
					message.validationMessageSource(ValidationConstants.REGISTER_NOT_FOUND_CUSTOM).replace("{table}",
							"Produto"));
		}

	}

	public ProductDTO productToDTO(Product product) {
		return this.mapper.toDtoMapper(product, ProductDTO.class);
	}

	public Product dtoToProduct(ProductDTO dto) {
		return this.mapper.toDomainMapper(dto, Product.class);
	}

	public List<ProductDTO> findAll() {
		List<Product> products = this.repository.findAll();
		List<ProductDTO> productsDTO = new ArrayList<>();
		if (products != null) {
			productsDTO = mapper.setListProductsDTO(products);
		}
		return productsDTO;
	}

	public Page<ProductDTO> findAllByFilter(Integer page, Integer size, String name, String description,
			BigDecimal lowestPrice, BigDecimal bigPrice, Pageable pageable, Integer pagina) {
		Page<Product> productsPage = Page.empty();
		productsPage = this.repository.findAllByFilter(name, description, lowestPrice, bigPrice, pageable, pagina);
		return this.mapper.setPageProductsDTO(productsPage);
	}

}
