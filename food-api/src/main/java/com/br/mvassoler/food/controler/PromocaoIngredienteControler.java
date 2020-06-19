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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.mvassoler.food.domain.model.PromocaoIngrediente;
import com.br.mvassoler.food.domain.service.PromocaoIngredienteService;
import com.br.mvassoler.food.exceptionhandler.ApiFoodExceptionPadrao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-promocao-ingrediente")
@Api(value = "API Promocao Ingrediente")
@CrossOrigin(origins = "*")
public class PromocaoIngredienteControler {

	@Autowired
	private PromocaoIngredienteService promocaoIngredienteService;

	@GetMapping("/promocoes")
	@ApiOperation(value = "Retorno da lista de todos as promocões por ingredientes")
	public List<PromocaoIngrediente> listar() {
		return this.promocaoIngredienteService.getPromocaoIngredienteRepository().findAll();
	}

	@GetMapping("/promocao/{id}")
	@ApiOperation(value = "Retorno de uma promocão por ingrediente pelo seu ID")
	public ResponseEntity<PromocaoIngrediente> getPromocaoIngrediente(@PathVariable(value = "id") Long id) {
		PromocaoIngrediente promocao = this.promocaoIngredienteService.getPromocaoIngredienteRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Ingrediente não encontrado"));
		if (promocao != null) {
			return ResponseEntity.ok(promocao);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/promocao")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Salva uma promocao de ingrediente")
	public PromocaoIngrediente salvaPromocaoIngrediente(@Valid @RequestBody PromocaoIngrediente promocao) {
		return this.promocaoIngredienteService.salvar(promocao);
	}

	@DeleteMapping("/promocao/{id}")
	@ApiOperation(value = "Exclui uma promoção de ingrediente")
	public ResponseEntity<Void> deletePromocaoIngrediente(@PathVariable Long id) {
		if (!this.promocaoIngredienteService.getPromocaoIngredienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.promocaoIngredienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
