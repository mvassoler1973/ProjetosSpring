package com.br.mvassoler.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.model.PromocaoItem;
import com.br.mvassoler.food.domain.repository.PromocaoItemRepository;

@Service
public class PromocaoItemService implements ServiceGeneric<PromocaoItem, Long> {

	@Autowired
	private PromocaoItemRepository promocaoItemRepository;

	public PromocaoItemRepository getPromocaoItemRepository() {
		return promocaoItemRepository;
	}

	@Override
	public PromocaoItem salvar(PromocaoItem entity) {
		return this.promocaoItemRepository.save(entity);
	}

	@Override
	public PromocaoItem atualizar(PromocaoItem entity) {
		return this.promocaoItemRepository.save(entity);
	}

	@Override
	public void excluir(Long id) {
		this.promocaoItemRepository.deleteById(id);

	}

}
