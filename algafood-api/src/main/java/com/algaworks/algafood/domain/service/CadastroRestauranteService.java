package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CozinhaRepository cozinhaRep;

	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = this.cozinhaRep.buscar(restaurante.getCozinha().getId());
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha não encontrada para o código %d.", restaurante.getCozinha().getId()));
		}
		restaurante.setCozinha(cozinha);
		return this.repository.salvar(restaurante);
	}

	public void excluir(Long id) {
		try {
			this.repository.remover(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe o restaurante com código %d.", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, pois esté em uso.", id));
		}

	}

}
