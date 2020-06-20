package com.br.mvassoler.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.model.Ingrediente;
import com.br.mvassoler.food.domain.repository.IngredienteRepository;
import com.br.mvassoler.food.exceptionhandler.ApiFoodExceptionPadrao;

@Service
public class IngredienteService implements ServiceGeneric<Ingrediente, Long> {

	@Autowired
	private IngredienteRepository ingredienteRepository;

	public IngredienteRepository getIngredienteRepository() {
		return ingredienteRepository;
	}

	@Override
	public Ingrediente salvar(Ingrediente entity) {
		Ingrediente ingrediente = this.ingredienteRepository.findByDescricao(entity.getDescricao());
		if (ingrediente != null && !ingrediente.equals(entity)) {
			throw new ApiFoodExceptionPadrao("Ingrediente com a descricao -> " + entity.getDescricao()
					+ " <- encontrado no Banco de Dados. Alterar a descrição.");
		}
		return this.ingredienteRepository.save(entity);
	}

	@Override
	public Ingrediente atualizar(Ingrediente entity) {
		return this.ingredienteRepository.save(entity);
	}

	@Override
	public void excluir(Long id) {
		this.ingredienteRepository.deleteById(id);
	}

	public List<Ingrediente> salvarListaIngredientes(List<Ingrediente> ingredientes) {
		return this.ingredienteRepository.saveAll(ingredientes);
	}

}
