package com.algaworks.algafood.api.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaControler {

	@Autowired
	private CozinhaRepository cozRep;

	@GetMapping
	public List<Cozinha> listar() {
		return this.cozRep.listar();
	}

	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable(name = "cozinhaId") Long cozinhaId) {
		return this.cozRep.buscar(cozinhaId);
	}

}
