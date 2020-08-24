package com.algaworks.algafood.api.controler;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.algaworks.algafood.api.model.CozinhaWrapperXml;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaControler {

	@Autowired
	private CozinhaRepository cozRep;

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Cozinha> listar() {
		return this.cozRep.listar();
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhaWrapperXml listarXml() {
		return new CozinhaWrapperXml(this.cozRep.listar());
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable(name = "cozinhaId") Long cozinhaId) {
		Cozinha cozinha = this.cozRep.buscar(cozinhaId);
		// return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return this.cozRep.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable(name = "cozinhaId") Long cozinhaId,
			@RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = this.cozRep.buscar(cozinhaId);
		if (cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			this.cozRep.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> excluir(@PathVariable(name = "cozinhaId") Long cozinhaId) {
		Cozinha cozinhaAtual = this.cozRep.buscar(cozinhaId);
		if (cozinhaAtual != null) {
			try {
				this.cozRep.remover(cozinhaAtual);
				return ResponseEntity.noContent().build();
			} catch (DataIntegrityViolationException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

}
