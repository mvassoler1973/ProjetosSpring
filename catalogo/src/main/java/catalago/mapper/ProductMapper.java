package catalago.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import catalago.dto.ProductDTO;
import catalago.model.Product;

@Component
public class ProductMapper implements TransformerModelMapper<ProductDTO, Product> {

	public List<ProductDTO> setListProductsDTO(List<Product> products) {
		List<ProductDTO> productsDTO = new ArrayList<>();
		products.forEach(product -> {
			productsDTO.add(this.toDtoMapper(product, ProductDTO.class));
		});
		return productsDTO;
	}

	public Page<ProductDTO> setPageProductsDTO(Page<Product> pageProducts) {
		return pageProducts.map((product) -> {
			return this.toDtoMapper(product, ProductDTO.class);
		});
	}

}
