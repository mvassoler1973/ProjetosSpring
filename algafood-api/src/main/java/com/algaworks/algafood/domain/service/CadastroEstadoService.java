package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {
		return this.repository.salvar(estado);
	}

	public void excluir(Long id) {
		try {
			this.repository.remover(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe a estado com código %d.", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois esté em uso.", id));
		}
	}

}
