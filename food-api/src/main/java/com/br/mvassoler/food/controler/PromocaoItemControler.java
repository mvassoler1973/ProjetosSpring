
package com.br.mvassoler.food.controler;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.mvassoler.food.domain.model.PromocaoItem;
import com.br.mvassoler.food.domain.service.PromocaoItemService;
import com.br.mvassoler.food.dto.PromocaoItemDto;
import com.br.mvassoler.food.exceptionhandler.ApiFoodExceptionPadrao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-promocao-item")
@Api(value = "API Promocao Item")
@CrossOrigin(origins = "*")
public class PromocaoItemControler {

	@Autowired
	private PromocaoItemService promocaoItemService;

	@GetMapping("/promocoes")
	@ApiOperation(value = "Retorno da lista de todos as promocões por item")
	public List<PromocaoItem> listar() {
		return this.promocaoItemService.getPromocaoItemRepository().findAll();
	}

	@GetMapping("/promocao/{id}")
	@ApiOperation(value = "Retorno de uma promocão por item pelo seu ID")
	public ResponseEntity<PromocaoItem> getPromocaoItem(@PathVariable(value = "id") Long id) {
		PromocaoItem promocao = this.promocaoItemService.getPromocaoItemRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Ingrediente não encontrado"));
		if (promocao != null) {
			return ResponseEntity.ok(promocao);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/promocao")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Salva uma promocao de item")
	public PromocaoItemDto salvaPromocaoItem(@Valid @RequestBody PromocaoItemDto promocao) {
		LocalDate datanow = LocalDate.now();
		System.out.println(datanow);
		return this.promocaoItemService.gravarPromocaoItem(promocao);
	}

	@DeleteMapping("/promocao/{id}")
	@ApiOperation(value = "Exclui uma promoção de item")
	public ResponseEntity<Void> deletePromocaoItem(@PathVariable Long id) {
		if (!this.promocaoItemService.getPromocaoItemRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.promocaoItemService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
