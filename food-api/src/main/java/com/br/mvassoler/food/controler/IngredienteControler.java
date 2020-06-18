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

import com.br.mvassoler.food.domain.model.Ingrediente;
import com.br.mvassoler.food.domain.service.IngredienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-ingrediente")
@Api(value = "API Ingrediente")
@CrossOrigin(origins = "*")
public class IngredienteControler {

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping("/ingredientes")
	@ApiOperation(value = "Retorno da lista de todos os ingredientes")
	public List<Ingrediente> getAllIngredientes() {
		return this.ingredienteService.getIngredienteRepository().findAll();
	}

	@GetMapping("/ingredientes/{descricao}")
	@ApiOperation(value = "Retorno da lista de todos os ingredientes pela descrição")
	public List<Ingrediente> getIngredienteByDescricao(@PathVariable(value = "descricao") String descricao) {
		return this.ingredienteService.getIngredienteRepository().findByDescricaoContaining(descricao);
	}

	@GetMapping("/ingrediente/{id}")
	@ApiOperation(value = "Retorno de um ingrediente pelo seu ID")
	public ResponseEntity<Ingrediente> getIngrediente(@PathVariable(value = "id") long id) {
		Ingrediente ingrediente = this.ingredienteService.getIngredienteRepository().findById(id);
		if (ingrediente != null) {
			return ResponseEntity.ok(ingrediente);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/ingrediente")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Salva um ingrediente")
	public Ingrediente salvaIngrediente(@Valid @RequestBody Ingrediente ingrediente) {
		return this.ingredienteService.salvar(ingrediente);
	}

	@PutMapping("/ingrediente/{id}")
	@ApiOperation(value = "Atualiza um ingrediente")
	public ResponseEntity<Ingrediente> atualizaIngrediente(@Valid @PathVariable Long id,
			@RequestBody Ingrediente ingrediente) {
		if (!this.ingredienteService.getIngredienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		ingrediente.setId(id);
		ingrediente = this.ingredienteService.atualizar(ingrediente);
		return ResponseEntity.ok(ingrediente);
	}

	@DeleteMapping("/ingrediente/{id}")
	@ApiOperation(value = "Exclui um ingrediente")
	public ResponseEntity<Void> deleteIngrediente(@PathVariable Long id) {
		if (!this.ingredienteService.getIngredienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.ingredienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
