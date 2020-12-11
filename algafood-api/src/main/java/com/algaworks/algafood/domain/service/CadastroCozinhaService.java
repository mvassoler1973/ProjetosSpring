package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_NAO_PERMITE_EXCLUSAO = "Cozinha de código %d não pode ser removida, pois esté em uso.";
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe a cozinha com código %d.";

	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return this.repository.save(cozinha);
	}

	public void excluir(Long id) {
		try {
			this.repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new CozinhaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_NAO_PERMITE_EXCLUSAO, id));
		}

	}

	public Cozinha buscar(Long cozinhaId) {
		return repository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}
