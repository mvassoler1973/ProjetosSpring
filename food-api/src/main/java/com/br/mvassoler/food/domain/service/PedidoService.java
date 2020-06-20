package com.br.mvassoler.food.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mvassoler.food.domain.dao.PedidoDao;
import com.br.mvassoler.food.domain.dao.PromocaoItemDao;
import com.br.mvassoler.food.domain.enumeradores.PedidoStatus;
import com.br.mvassoler.food.domain.model.Item;
import com.br.mvassoler.food.domain.model.Pedido;
import com.br.mvassoler.food.domain.model.PedidoItem;
import com.br.mvassoler.food.domain.model.PromocaoItem;
import com.br.mvassoler.food.domain.repository.PedidoItemRepository;
import com.br.mvassoler.food.domain.repository.PedidoRepository;
import com.br.mvassoler.food.dto.PedidoInDto;
import com.br.mvassoler.food.dto.PedidoItemDto;
import com.br.mvassoler.food.dto.PedidoOutDto;

@Service
public class PedidoService implements ServiceGeneric<Pedido, Long> {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoItemRepository pedidoItemRepository;

	@Autowired
	private PedidoDao pedidoDao;

	@Autowired
	private PromocaoItemDao promocaoItemDao;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ModelMapper modelMapper;

	public PedidoRepository getPedidoRepository() {
		return pedidoRepository;
	}

	public PedidoItemRepository getPedidoItemRepository() {
		return pedidoItemRepository;
	}

	@Override
	public Pedido salvar(Pedido entity) {
		return this.pedidoRepository.save(entity);
	}

	@Override
	public Pedido atualizar(Pedido entity) {
		return this.pedidoRepository.save(entity);
	}

	@Override
	public void excluir(Long id) {
		this.pedidoRepository.deleteById(id);
	}

	public PedidoOutDto openPedido(PedidoInDto pedidoIntDto) {
		Pedido pedido = this.toModelByPedidoIn(pedidoIntDto);
		pedido.setDataHora(LocalDateTime.now());
		pedido.setStatus(PedidoStatus.ABERTO);
		pedido.setValorFinal(BigDecimal.ZERO);
		this.pedidoRepository.save(pedido);
		PedidoOutDto pedidoOutDto = this.toPedidoByModel(pedido);
		return pedidoOutDto;
	}

	public List<Pedido> getAllPedidos() {
		List<Pedido> pedidos = this.pedidoRepository.findAll();
		for (Pedido pedido : pedidos) {
			pedido = this.getItensPedido(pedido);
			// pedido.setPedidoItens(this.pedidoDao.getAllIItemByIdPedido(pedido.getId()));
		}
		return pedidos;
	}

	private Pedido toModelByPedido(PedidoOutDto pedidoOutDto) {
		return this.modelMapper.map(pedidoOutDto, Pedido.class);
	}

	private PedidoOutDto toPedidoByModel(Pedido pedido) {
		return this.modelMapper.map(pedido, PedidoOutDto.class);
	}

	private Pedido toModelByPedidoIn(PedidoInDto pedidoinDto) {
		return this.modelMapper.map(pedidoinDto, Pedido.class);
	}

	public Pedido getPreviewPedido(Pedido pedido) {
		pedido.setValorFinal(this.calcularValorTotal(pedido.getPedidoItens()));
		pedido.setValorPromocao(this.calcularDescontoPromocaoItem(pedido.getPedidoItens()));
		pedido.setValorFinal(pedido.getValorFinal().subtract(pedido.getValorPromocao()));
		this.pedidoRepository.save(pedido);
		return pedido;
	}

	public Pedido setFecharPedido(Pedido pedido) {
		pedido.setStatus(PedidoStatus.FECHADO);
		this.pedidoRepository.save(pedido);
		return pedido;
	}

	public BigDecimal calcularValorTotal(List<PedidoItem> pedidoItens) {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (PedidoItem item : pedidoItens) {
			valorTotal = valorTotal.add(item.getPrecoTotal().subtract(item.getValorDesconto()));
		}
		return valorTotal;
	}

	// Processos do item do pedido
	public Pedido getItensPedido(Pedido pedido) {
		pedido.setPedidoItens(this.pedidoDao.getAllIItemByIdPedido(pedido.getId()));
		for (PedidoItem pedidoItem : pedido.getPedidoItens()) {
			pedidoItem.getItem()
					.setItemIngredientes(this.itemService.getItemIngredientesByIdItem(pedidoItem.getItem().getId()));
		}
		return pedido;
	}

	private PedidoItem toModelByPedidoItem(PedidoItemDto pedidoItemDto) {
		return this.modelMapper.map(pedidoItemDto, PedidoItem.class);
	}

	private PedidoItemDto toPedidoItemByModel(PedidoItem pedidoItem) {
		return this.modelMapper.map(pedidoItem, PedidoItemDto.class);
	}

	private List<PedidoItem> toModelByPedidoItens(List<PedidoItemDto> pedidoItensDto) {
		return pedidoItensDto.stream().map(pedidoItemDto -> this.toModelByPedidoItem(pedidoItemDto))
				.collect(Collectors.toList());
	}

	private List<PedidoItemDto> toModelByPedidoItensDto(List<PedidoItem> pedidoItens) {
		return pedidoItens.stream().map(pedidoItem -> this.toPedidoItemByModel(pedidoItem))
				.collect(Collectors.toList());
	}

	public List<PedidoItemDto> addItemOnPedido(List<PedidoItemDto> pedidoItensDto) {
		List<PedidoItem> pedidoItens = this.toModelByPedidoItens(pedidoItensDto);
		this.pedidoItemRepository.saveAll(pedidoItens);
		pedidoItensDto = this.toModelByPedidoItensDto(pedidoItens);
		return pedidoItensDto;
	}

	// processo para calculo dos descontos das promocoes
	public BigDecimal calcularDescontoPromocaoItem(List<PedidoItem> pedidoItem) {
		BigDecimal valorDescontoPromocao = BigDecimal.ZERO;
		Map<Item, BigDecimal> lista = pedidoItem.stream().collect(Collectors.groupingBy(PedidoItem::getItem,
				Collectors.reducing(BigDecimal.ZERO, PedidoItem::getQuantidade, BigDecimal::add)));
		for (Map.Entry<Item, BigDecimal> saida : lista.entrySet()) {
			Item item = saida.getKey();
			if (item != null) {
				PromocaoItem promocaoItem = this.promocaoItemDao
						.getPromocaoIngredienteByIngredienteDataFinalValidade(item, LocalDate.now());
				if (promocaoItem != null) {
					if ((saida.getValue().longValue() / promocaoItem.getQuantidade().longValue()) >= 1) {
						valorDescontoPromocao = valorDescontoPromocao.add(promocaoItem.getValorPromocao());
					}
				}
			}
		}
		return valorDescontoPromocao;
	}

}
