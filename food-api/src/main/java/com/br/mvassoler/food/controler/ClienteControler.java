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

import com.br.mvassoler.food.domain.model.Cliente;
import com.br.mvassoler.food.domain.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-cliente")
@Api(value = "API Cliente")
@CrossOrigin(origins = "*")
public class ClienteControler {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/clientes")
	@ApiOperation(value = "Retorno da lista de todos os clientes")
	public List<Cliente> getAllCliente() {
		return this.clienteService.getClienteRepository().findAll();
	}

	@GetMapping("/cliente-nome/{nome}")
	@ApiOperation(value = "Retorno da lista de todos os clientes pelo nome")
	public List<Cliente> getClienteByNome(@PathVariable(value = "nome") String nome) {
		return this.clienteService.getClienteRepository().findByNomeContaining(nome);
	}

	@GetMapping("/cliente-cep/{cep}")
	@ApiOperation(value = "Retorno da lista de todos os clientes pelo CEP")
	public List<Cliente> getClienteByCep(@PathVariable(value = "cep") Integer cep) {
		return this.clienteService.getClienteRepository().findByCep(cep);
	}

	@GetMapping("/cliente/{id}")
	@ApiOperation(value = "Retorno de um cliente pelo seu ID")
	public ResponseEntity<Cliente> getCliente(@PathVariable(value = "id") long id) {
		Cliente cliente = this.clienteService.getClienteRepository().findById(id);
		if (cliente != null) {
			return ResponseEntity.ok(cliente);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/cliente")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Salva um cliente")
	public Cliente salvaIngrediente(@Valid @RequestBody Cliente cliente) {
		return this.clienteService.salvar(cliente);
	}

	@PutMapping("/cliente/{id}")
	@ApiOperation(value = "Atualiza um cliente")
	public ResponseEntity<Cliente> atualizaIngrediente(@Valid @PathVariable Long id, @RequestBody Cliente cliente) {
		if (!this.clienteService.getClienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		cliente = this.clienteService.atualizar(cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/cliente/{id}")
	@ApiOperation(value = "Exclui um cliente")
	public ResponseEntity<Void> deleteIngrediente(@PathVariable Long id) {
		if (!this.clienteService.getClienteRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
