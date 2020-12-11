package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";

	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {
		return this.repository.save(estado);
	}

	public void excluir(Long estadoId) {
		try {
			this.repository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradaException(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}

	public Estado buscar(Long estadoId) {
		return repository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradaException(estadoId));
	}

}
