package catalago.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import catalago.business.ProductBusiness;
import catalago.core.BaseConverter;
import catalago.core.dto.DeletedDTO;
import catalago.core.dto.PageDTO;
import catalago.dto.ProductDTO;
import catalago.model.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	private final ProductBusiness business;

	public ProductService(ProductBusiness business) {
		super();
		this.business = business;
	}

	public ProductDTO add(ProductDTO product) {
		log.info("Gravando o novo produto " + product.getName() + ".");
		Product prod = this.business.dtoToProduct(product);
		return this.business.productToDTO(this.business.getRepository().save(prod));
	}

	public ProductDTO update(Long id, ProductDTO product) {
		log.info("Atualizando o produto " + product.getName() + ".");

		this.business.getProductById(id);
		product.setId(id);
		Product prod = this.business.dtoToProduct(product);
		return this.business.productToDTO(this.business.getRepository().save(prod));

	}

	public ProductDTO findById(Long id) {
		log.info("Pesquisando o produto de ID " + id.toString() + ".");
		return this.business.productToDTO(this.business.getProductById(id));
	}

	public List<ProductDTO> findAll() {
		log.info("Pesquisando todos os produtos.");
		return this.business.findAll();
	}

	public PageDTO<ProductDTO> findAllByFilter(Integer page, Integer size, String name, String description,
			BigDecimal lowestPrice, BigDecimal bigPrice) {
		log.info("Pesquisando os produtos por filtros.");
		Page<ProductDTO> productPaged = Page.empty();
		productPaged = this.business.findAllByFilter(page, size, name, description, lowestPrice, bigPrice,
				this.createSearchPageRequest(page, size), page);
		return BaseConverter.toPageVO(productPaged);
	}

	public DeletedDTO delete(Long id) {
		log.info("Excluíndo o produto de ID " + id.toString() + ".");
		try {
			Product prod = this.business.getProductById(id);
			this.business.getRepository().deleteById(id);
			return DeletedDTO.setNewDeletedDTO(prod);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Pageable createSearchPageRequest(Integer page, Integer size) {
		if (page == null || size == null) {
			return Pageable.unpaged();
		}
		return PageRequest.of(page, size);
	}

}
