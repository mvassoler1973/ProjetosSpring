package com.br.mvassoler.food.controler;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.mvassoler.food.domain.model.Item;
import com.br.mvassoler.food.domain.model.ItemIngrediente;
import com.br.mvassoler.food.domain.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-item")
@Api(value = "API Item")
@CrossOrigin(origins = "*")
public class ItemControler {

	@Autowired
	private ItemService itemService;

	@GetMapping("/itens")
	@ApiOperation(value = "Retorno da lista de todos os itens")
	public List<Item> listar() {
		return this.itemService.getAllItens();
	}

	@GetMapping("/itens/{descricao}")
	@ApiOperation(value = "Retorno da lista de todos os itens pela descrição")
	public List<Item> getItemByDescricao(@PathVariable(value = "descricao") String descricao) {
		return this.itemService.getAllItensDescricao(descricao);
	}

	@GetMapping("/item/{id}")
	@ApiOperation(value = "Retorno de um item pelo seu ID")
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") long id) {
		Item item = this.itemService.getItemRepository().findById(id);
		if (item != null) {
			item = this.itemService.getIngredientesItem(item);
			return ResponseEntity.ok(item);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/item")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Salva um item")
	public Item salvaItem(@Valid @RequestBody Item item) {
		return this.itemService.salvar(item);
	}

	@PutMapping("/item/{id}")
	@ApiOperation(value = "Atualiza um item")
	public ResponseEntity<Item> atualizaItem(@Valid @PathVariable Long id, @RequestBody Item item) {
		if (!this.itemService.getItemRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		item.setId(id);
		item = this.itemService.atualizar(item);
		return ResponseEntity.ok(item);
	}

	@DeleteMapping("/item/{id}")
	@ApiOperation(value = "Exclui um item")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		if (!this.itemService.getItemRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.itemService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/itemIngredientes/{id}")
	@ApiOperation(value = "Retorno da lista de todos os ingredientes de um item")
	public List<ItemIngrediente> listarItensIngredientesByIdItem(@PathVariable(value = "id") long id) {
		return this.itemService.getItemIngredientesByIdItem(id);
	}

	@PostMapping("/itemIngrediente/{id}")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Acrescenta um item-ingrediente para um item pelo ID do item")
	public ResponseEntity<ItemIngrediente> salvaItemIngrediente(@Valid @PathVariable Long id,
			@RequestBody ItemIngrediente ingrediente) {
		if (!this.itemService.getItemRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Item item = this.itemService.getItemRepository().findById(id.longValue());
		ingrediente.setItem(item);
		this.itemService.salvarIngrediente(ingrediente);
		return ResponseEntity.ok(ingrediente);
	}

	@PutMapping("/itemIngrediente/{id}")
	@ApiOperation(value = "Atualiza um item-ingrediente para um item pelo ID do item")
	public ResponseEntity<ItemIngrediente> atualizaItemIngrediente(@Valid @PathVariable Long id,
			@RequestBody ItemIngrediente ingrediente) {
		if (!this.itemService.getItemIngredienteRepository().existsById(ingrediente.getId())) {
			return ResponseEntity.notFound().build();
		}
		Item item = this.itemService.getItemRepository().findById(id.longValue());
		ingrediente.setItem(item);
		ingrediente = this.itemService.atualizarIngrediente(ingrediente);
		return ResponseEntity.ok(ingrediente);
	}

	@DeleteMapping("/itemIngrediente/{id}")
	@ApiOperation(value = "Exclui um ingrediente de um item")
	public ResponseEntity<Void> deleteItemIngrediente(@PathVariable Long id) {
		if (!this.itemService.getItemIngredienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.itemService.excluirIngrediente(id);
		return ResponseEntity.noContent().build();
	}
}
