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
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/restaurantes", produces = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE })
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRep;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Cidade> listar() {
		return this.cidadeRep.listar();
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable(name = "cidadeId") Long cidadeId) {
		Cidade cidade = this.cidadeRep.buscar(cidadeId);
		if (cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.cadastroCidadeService.salvar(cidade));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable(name = "cidadeId") Long cidadeId, @RequestBody Cidade cidade) {
		try {
			Cidade cidadeAtual = this.cidadeRep.buscar(cidadeId);
			if (cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				this.cadastroCidadeService.salvar(cidadeAtual);
				return ResponseEntity.ok(cidadeAtual);
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Restaurante> excluir(@PathVariable(name = "cidadeId") Long cidadeId) {
		try {
			this.cadastroCidadeService.excluir(cidadeId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

}
