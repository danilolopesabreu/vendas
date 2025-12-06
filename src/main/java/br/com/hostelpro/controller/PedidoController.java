package br.com.hostelpro.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hostelpro.dto.PedidoDTO;
import br.com.hostelpro.entity.Pedido;
import br.com.hostelpro.mapper.PedidoMapper;
import br.com.hostelpro.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
    private PedidoService service;
    
	@Autowired
	private PedidoMapper mapper;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO dto) {
        Pedido entidade = mapper.toEntity(dto);
        Pedido salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @PutMapping
    public ResponseEntity<PedidoDTO> atualizarPedido(@Valid @RequestBody PedidoDTO dto) {
        Pedido entidade = mapper.toEntity(dto);
        Pedido salvo = service.atualizar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<PedidoDTO>> listarPorEstabelecimentoOrderDataCriacaoStatusOrdenado(@PathVariable Integer estabelecimentoId) {
        return ResponseEntity.ok(service.listarPorEstabelecimentoOrderDataCriacaoStatusOrdenado(estabelecimentoId).stream().map(mapper::toDTO).collect(Collectors.toList()));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody PedidoDTO dto) {
        Pedido entidade = mapper.toEntity(dto);
        Pedido atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/item-pedido/cancelar/{id}")
    public ResponseEntity<Void> cancelarItemPedido(@PathVariable Integer id) {
    	service.cancelarItemPedido(id);
    	return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/item-pedido/{id}")
    public ResponseEntity<Void> deletarItemPedido(@PathVariable Integer id) {
    	service.deletarItemPedido(id);
    	return ResponseEntity.noContent().build();
    }
}
