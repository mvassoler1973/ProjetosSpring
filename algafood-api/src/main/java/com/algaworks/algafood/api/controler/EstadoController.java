package com.algaworks.algafood.api.controler;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class EstadoController {

	@Autowired
	private EstadoRepository estRep;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Estado> listar() {
		return this.estRep.listar();
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable(name = "estadoId") Long estadoId) {
		Estado estado = this.estRep.buscar(estadoId);
		// return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		if (estado != null) {
			return ResponseEntity.ok(estado);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return this.cadastroEstadoService.salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable(name = "estadoId") Long estadoId,
			@RequestBody Estado cozinha) {
		Estado estadoAtual = this.estRep.buscar(estadoId);
		if (estadoAtual != null) {
			BeanUtils.copyProperties(cozinha, estadoAtual, "id");
			this.cadastroEstadoService.salvar(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Cozinha> excluir(@PathVariable(name = "estadoId") Long estadoId) {
		try {
			this.cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
