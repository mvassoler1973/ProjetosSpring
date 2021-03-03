package catalago.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import catalago.business.ProductBusiness;
import catalago.core.BaseConverter;
import catalago.core.constants.ValidationConstants;
import catalago.core.dto.DeletedDTO;
import catalago.core.dto.PageDTO;
import catalago.core.exception.EntidadeEmUsoException;
import catalago.core.exception.ProductsNaoEncontradaException;
import catalago.core.exception.RangeFiltersException;
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
		Product prod = this.business.getProductById(id);
		LocalDateTime created = prod.getCreated();
		product.setId(id);
		prod = this.business.dtoToProduct(product);
		prod.setCreated(created);
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
		if (lowestPrice.compareTo(bigPrice) == 1) {
			throw new RangeFiltersException(ValidationConstants.RANGE_FILTER_INVALID);
		}
		Page<ProductDTO> productPaged = Page.empty();
		productPaged = this.business.findAllByFilter(page, size, name, description, lowestPrice, bigPrice,
				this.createSearchPageRequest(page, size), page);
		return BaseConverter.toPageVO(productPaged);
	}

	public DeletedDTO delete(Long id) {
		Product prod = this.business.getProductById(id);
		try {
			this.business.getRepository().deleteById(id);
			return DeletedDTO.setNewDeletedDTO(prod);
		} catch (EmptyResultDataAccessException ex) {
			throw new ProductsNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ValidationConstants.MSG_PRODUTO_EM_USO, id));
		}
	}

	private Pageable createSearchPageRequest(Integer page, Integer size) {
		if (page == null || size == null) {
			return Pageable.unpaged();
		}
		return PageRequest.of(page, size);
	}

}
