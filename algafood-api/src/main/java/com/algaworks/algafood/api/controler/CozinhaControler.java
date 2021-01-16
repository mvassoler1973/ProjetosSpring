package com.algaworks.algafood.api.controler;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaControler {

	@Autowired
	private CozinhaRepository cozRep;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Cozinha> listar() {
		return this.cozRep.findAll();
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhaWrapperXml listarXml() {
		return new CozinhaWrapperXml(this.cozRep.findAll());
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable(name = "cozinhaId") Long cozinhaId) {
		return this.cadastroCozinhaService.buscar(cozinhaId);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return this.cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable(name = "cozinhaId") Long cozinhaId, @RequestBody @Valid Cozinha cozinha) {
		Cozinha cozinhaAtual = this.cadastroCozinhaService.buscar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return this.cadastroCozinhaService.salvar(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(name = "cozinhaId") Long cozinhaId) {
		this.cadastroCozinhaService.excluir(cozinhaId);
	}

}
