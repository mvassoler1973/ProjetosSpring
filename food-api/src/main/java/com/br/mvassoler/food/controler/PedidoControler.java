package com.br.mvassoler.food.controler;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.mvassoler.food.domain.enumeradores.PedidoStatus;
import com.br.mvassoler.food.domain.model.Pedido;
import com.br.mvassoler.food.domain.service.PedidoService;
import com.br.mvassoler.food.dto.PedidoInDto;
import com.br.mvassoler.food.dto.PedidoItemDto;
import com.br.mvassoler.food.dto.PedidoOutDto;
import com.br.mvassoler.food.exceptionhandler.ApiFoodExceptionPadrao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api-pedido")
@Api(value = "API Pedido")
@CrossOrigin(origins = "*")
public class PedidoControler {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/pedidos")
	@ApiOperation(value = "Retorno da lista de todos os pedidos")
	public List<Pedido> listar() {
		return this.pedidoService.getAllPedidos();
	}

	@GetMapping("/pedido/{id}")
	@ApiOperation(value = "Retorno de um pedido pelo seu ID")
	public ResponseEntity<Pedido> getPedido(@PathVariable(value = "id") Long id) {
		Pedido pedido = this.pedidoService.getPedidoRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Pedido não encontrado"));
		if (pedido != null) {
			pedido = this.pedidoService.getItensPedido(pedido);
			return ResponseEntity.ok(pedido);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/pedido-preview/{id}")
	@ApiOperation(value = "Retorno de um pedido pelo seu ID para um preview")
	public ResponseEntity<Pedido> getPedidoByPreview(@PathVariable(value = "id") Long id) {
		Pedido pedido = this.pedidoService.getPedidoRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Pedido não encontrado"));
		if (pedido != null) {
			pedido = this.pedidoService.getItensPedido(pedido);
			pedido = this.pedidoService.getPreviewPedido(pedido);
			return ResponseEntity.ok(pedido);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/pedido")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Abrir um novo pedido")
	public PedidoOutDto abrirPedido(@Valid @RequestBody PedidoInDto pedidointDto) {
		return this.pedidoService.openPedido(pedidointDto);
	}

	@PutMapping("/pedido/{id}")
	@ApiOperation(value = "Cancelar um pedido")
	public ResponseEntity<Pedido> cancelarPedido(@Valid @PathVariable Long id) {
		Pedido pedido = this.pedidoService.getPedidoRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Pedido não encontrado"));
		if (pedido != null) {
			pedido.setStatus(PedidoStatus.CANCELADO);
			pedido = this.pedidoService.atualizar(pedido);
			return ResponseEntity.ok(pedido);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/pedido-close/{id}")
	@ApiOperation(value = "Fechamento de um pedido pelo seu ID para um preview")
	public ResponseEntity<Pedido> fecharPedido(@PathVariable(value = "id") Long id) {
		Pedido pedido = this.pedidoService.getPedidoRepository().findById(id)
				.orElseThrow(() -> new ApiFoodExceptionPadrao("Pedido não encontrado"));
		if (pedido != null) {
			if (pedido.getStatus().equals(PedidoStatus.FECHADO)) {
				throw new ApiFoodExceptionPadrao("Pedido com status de fechado.");
			}
			if (pedido.getStatus().equals(PedidoStatus.CANCELADO)) {
				throw new ApiFoodExceptionPadrao("Pedido com status de cancelado.");
			}
			pedido = this.pedidoService.getItensPedido(pedido);
			pedido = this.pedidoService.getPreviewPedido(pedido);
			return ResponseEntity.ok(pedido);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/pedidoItem")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Adicionar itens ao pedido")
	public List<PedidoItemDto> addItensPedido(@Valid @RequestBody List<PedidoItemDto> pedidoItensDto) {
		return this.pedidoService.addItemOnPedido(pedidoItensDto);
	}

	@DeleteMapping("/pedidoItem/{id}")
	@ApiOperation(value = "Exclui um item do pedido")
	public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
		if (!this.pedidoService.getPedidoItemRepository().existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		this.pedidoService.getPedidoItemRepository().deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
