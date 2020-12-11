package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida, pois esté em uso.";
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
		restaurante.setCozinha(cozinha);
		return repository.save(restaurante);
	}

	public void excluir(Long id) {
		try {
			this.repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new RestauranteNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		}

	}

	public Restaurante buscar(Long restauranteId) {
		return repository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}

}
