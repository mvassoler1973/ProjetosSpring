package com.br.mvassoler.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.model.PromocaoIngrediente;
import com.br.mvassoler.food.domain.repository.PromocaoIngredienteRepository;

@Service
public class PromocaoIngredienteService implements ServiceGeneric<PromocaoIngrediente, Long> {

	@Autowired
	private PromocaoIngredienteRepository promocaoIngredienteRepository;

	public PromocaoIngredienteRepository getPromocaoIngredienteRepository() {
		return promocaoIngredienteRepository;
	}

	@Override
	public PromocaoIngrediente salvar(PromocaoIngrediente entity) {
		return this.promocaoIngredienteRepository.save(entity);
	}

	@Override
	public PromocaoIngrediente atualizar(PromocaoIngrediente entity) {
		return this.promocaoIngredienteRepository.save(entity);
	}

	@Override
	public void excluir(Long id) {
		this.promocaoIngredienteRepository.deleteById(id);

	}

}
