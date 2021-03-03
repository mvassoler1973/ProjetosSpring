package catalago.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import catalago.core.constants.ValidationConstants;
import catalago.core.exception.EntidadeEmUsoException;
import catalago.core.exception.ProductsNaoEncontradaException;
import catalago.dto.ProductDTO;
import catalago.mapper.ProductMapper;
import catalago.model.Product;
import catalago.repository.ProductRepository;
import lombok.Data;

@Component
@Data
public class ProductBusiness {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductMapper mapper;

	public Product getProductById(Long id) {
		try {
			return this.repository.findById(id).get();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ValidationConstants.MSG_PRODUTO_EM_USO, id));
		} catch (NoSuchElementException e) {
			throw new ProductsNaoEncontradaException(String.format(ValidationConstants.MSG_PRODUTO_NAO_ENCONTRADO, id));
		} catch (Exception e) {
			throw new ProductsNaoEncontradaException(String.format(ValidationConstants.MSG_PRODUTO_NAO_ENCONTRADO, id));
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
