package com.br.mvassoler.food.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.model.PromocaoItem;
import com.br.mvassoler.food.domain.repository.PromocaoItemRepository;
import com.br.mvassoler.food.dto.PromocaoItemDto;

@Service
public class PromocaoItemService implements ServiceGeneric<PromocaoItem, Long> {

	@Autowired
	private PromocaoItemRepository promocaoItemRepository;

	@Autowired
	private ModelMapper modelMapper;

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

	public PromocaoItemDto gravarPromocaoItem(PromocaoItemDto promocaoDto) {
		PromocaoItem promocao = this.toModelByPromocao(promocaoDto);
		this.promocaoItemRepository.save(promocao);
		promocaoDto = this.toPromocaoByModel(promocao);
		return promocaoDto;
	}

	private PromocaoItem toModelByPromocao(PromocaoItemDto promocaoDto) {
		return this.modelMapper.map(promocaoDto, PromocaoItem.class);
	}

	private PromocaoItemDto toPromocaoByModel(PromocaoItem promocao) {
		return this.modelMapper.map(promocao, PromocaoItemDto.class);
	}

}
