package com.irisdual.pedidos_api.controller;

import java.util.List;

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

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.entity.Pedido;
import com.irisdual.pedidos_api.service.IPedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @PostMapping
    public PedidoDTO createPedido(@RequestBody Pedido pedido) {
        return pedidoService.createPedido(pedido);
    }

    // Nuevo endpoint para la HU002
    @GetMapping("/{id}")
    public PedidoDTO getPedidoById(@PathVariable Integer id) {
        return pedidoService.getPedidoById(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            PedidoDTO pedidoActualizado = pedidoService.updatePedido(id, pedido);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}