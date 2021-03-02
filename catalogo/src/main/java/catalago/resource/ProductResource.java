package catalago.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import catalago.core.dto.DeletedDTO;
import catalago.core.dto.PageDTO;
import catalago.dto.ProductDTO;
import catalago.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {

	private final ProductService service;

	public ProductResource(ProductService service) {
		super();
		this.service = service;
	}

	@ApiOperation(value = "create", notes = "Criar um novo produto.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO product) {
		return ResponseEntity.ok(this.service.add(product));
	}

	@ApiOperation(value = "update", notes = "Atualizar um produto.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
		return ResponseEntity.ok(this.service.update(id, product));
	}

	@ApiOperation(value = "findById", notes = "Busca um produto pelo seu ID.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(this.service.findById(id));
	}

	@ApiOperation(value = "findAll", notes = "Listar todos produtos.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not found") })
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAll());
	}

	@ApiOperation(value = "findAllByFilter", notes = "Retorna todos as solicitações em modo paginado.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not found") })
	@GetMapping(value = "/search")
	public ResponseEntity<PageDTO<ProductDTO>> findAllByFilter(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "lowestPrice", required = false) BigDecimal lowestPrice,
			@RequestParam(value = "bigPrice", required = false) BigDecimal bigPrice) {
		return ResponseEntity.ok()
				.body(this.service.findAllByFilter(page, size, name, description, lowestPrice, bigPrice));
	}

	@ApiOperation(value = "delete", notes = "Procede a exclusão do produto pelo seu ID.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DeletedDTO> delete(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.delete(id));
	}

}
