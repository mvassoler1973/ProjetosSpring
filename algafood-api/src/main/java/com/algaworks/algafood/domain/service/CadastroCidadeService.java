package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		Estado estado = this.estadoRepository.buscar(cidade.getEstado().getId());
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Estado não encontrada para o código %d.", cidade.getEstado().getId()));
		}
		cidade.setEstado(estado);
		return this.repository.salvar(cidade);
	}

	public void excluir(Long id) {
		try {
			this.repository.remover(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe o cidade com código %d.", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois esté em uso.", id));
		}

	}

}
