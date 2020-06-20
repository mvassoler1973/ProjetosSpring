package com.br.mvassoler.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.dao.ItemDao;
import com.br.mvassoler.food.domain.model.Item;
import com.br.mvassoler.food.domain.model.ItemIngrediente;
import com.br.mvassoler.food.domain.repository.ItemIngedienteRepository;
import com.br.mvassoler.food.domain.repository.ItemRepository;
import com.br.mvassoler.food.exceptionhandler.ApiFoodExceptionPadrao;

@Service
public class ItemService implements ServiceGeneric<Item, Long> {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemIngedienteRepository itemIngredienteRepository;

	@Autowired
	private ItemDao itemDao;

	public ItemRepository getItemRepository() {
		return itemRepository;
	}

	public ItemIngedienteRepository getItemIngredienteRepository() {
		return itemIngredienteRepository;
	}

	// Rotinas abaixo se referem a um item
	@Override
	public Item salvar(Item entity) {
		Item item = this.itemRepository.findByDescricao(entity.getDescricao());
		if (item != null && !item.equals(entity)) {
			throw new ApiFoodExceptionPadrao("Item com a descricao -> " + entity.getDescricao()
					+ " <- encontrado no Banco de Dados. Alterar a descrição.");
		}
		this.itemRepository.save(entity);
		for (ItemIngrediente ingrediente : entity.getItemIngredientes()) {
			ingrediente.setItem(entity);
		}
		this.itemIngredienteRepository.saveAll(entity.getItemIngredientes());
		return entity;
	}

	@Override
	public Item atualizar(Item entity) {
		this.itemRepository.save(entity);
		for (ItemIngrediente ingrediente : entity.getItemIngredientes()) {
			ingrediente.setItem(entity);
		}
		this.itemIngredienteRepository.saveAll(entity.getItemIngredientes());
		return entity;
	}

	@Override
	public void excluir(Long id) {
		List<ItemIngrediente> ingredientes = this.itemDao.getAllIngredienteByIdItem(id);
		this.itemIngredienteRepository.deleteAll(ingredientes);
		this.itemRepository.deleteById(id);
	}

	public List<Item> getAllItens() {
		List<Item> itens = this.itemRepository.findAll();
		for (Item item : itens) {
			item.setItemIngredientes(this.itemDao.getAllIngredienteByIdItem(item.getId()));
		}
		return itens;
	}

	public List<Item> getAllItensDescricao(String descricao) {
		List<Item> itens = this.itemRepository.findByDescricaoContaining(descricao);
		for (Item item : itens) {
			item.setItemIngredientes(this.itemDao.getAllIngredienteByIdItem(item.getId()));
		}
		return itens;
	}

	public void recalcularTotaisItem(long id) {
		Item item = this.itemRepository.findById(id);
		item.setItemIngredientes(this.itemDao.getAllIngredienteByIdItem(item.getId()));
		this.atualizar(item);
	}

	// Processos abaixo se referem aos ingredientes de um item
	public ItemIngrediente salvarIngrediente(ItemIngrediente entity) {
		this.itemIngredienteRepository.save(entity);
		this.recalcularTotaisItem(entity.getItem().getId().longValue());
		return entity;
	}

	public ItemIngrediente atualizarIngrediente(ItemIngrediente entity) {
		this.itemIngredienteRepository.save(entity);
		this.recalcularTotaisItem(entity.getItem().getId().longValue());
		return entity;
	}

	public void excluirIngrediente(Long id) {
		ItemIngrediente ingrediente = this.itemIngredienteRepository.findById(id.longValue());
		this.itemIngredienteRepository.delete(ingrediente);
		this.recalcularTotaisItem(ingrediente.getItem().getId().longValue());
	}

	public Item getIngredientesItem(Item item) {
		item.setItemIngredientes(this.itemDao.getAllIngredienteByIdItem(item.getId()));
		return item;
	}

	public List<ItemIngrediente> getItemIngredientesByIdItem(Long id) {
		return this.itemDao.getAllIngredienteByIdItem(id);
	}

}
