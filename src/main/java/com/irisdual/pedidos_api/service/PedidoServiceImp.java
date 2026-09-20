package com.irisdual.pedidos_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Import agregado

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.dto.mapper.PedidoMapper;
import com.irisdual.pedidos_api.entity.Pedido;
import com.irisdual.pedidos_api.entity.Pieza;
import com.irisdual.pedidos_api.repository.PedidoRepository;
import com.irisdual.pedidos_api.repository.PiezaRepository;

@Service
@Transactional // <-- Anotación agregada para evitar LazyInitializationException
public class PedidoServiceImp implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PiezaRepository piezaRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Override
    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> pedidoMapper.pedidoAPedidoDTO(pedido))
                .toList();
    }

    @Override
    public PedidoDTO createPedido(Pedido pedido) {
        List<Pieza> piezasReales = new ArrayList<>();
        
        for (Pieza p : pedido.getPiezas()) {
            Pieza piezaDb = piezaRepository.findById(p.getId())
                    .orElseThrow(() -> new RuntimeException("Error: La pieza con ID " + p.getId() + " no existe."));
            piezasReales.add(piezaDb);
        }
        
        pedido.setPiezas(piezasReales);
        
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.pedidoAPedidoDTO(pedidoGuardado); 
    }

    @Override
    public PedidoDTO getPedidoById(Integer id) {
        return pedidoRepository.findById(id)
                .map(pedido -> pedidoMapper.pedidoAPedidoDTO(pedido))
                .orElse(null);
    }
    
    
    @Override
    public PedidoDTO updatePedido(Integer id, Pedido pedidoDetalles) {
        // 1. Buscamos el pedido existente en la base de datos
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El pedido con ID " + id + " no existe."));

        // 2. Actualizamos los campos básicos
        pedidoExistente.setNombreCliente(pedidoDetalles.getNombreCliente());
        pedidoExistente.setFechaPedido(pedidoDetalles.getFechaPedido());

        // 3. Re-vinculamos las piezas reales para actualizar la relación
        List<Pieza> piezasReales = new ArrayList<>();
        for (Pieza p : pedidoDetalles.getPiezas()) {
            Pieza piezaDb = piezaRepository.findById(p.getId())
                    .orElseThrow(() -> new RuntimeException("Error: La pieza con ID " + p.getId() + " no existe."));
            piezasReales.add(piezaDb);
        }
        pedidoExistente.setPiezas(piezasReales);

        // 4. Guardamos los cambios y devolvemos el DTO
        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);
        return pedidoMapper.pedidoAPedidoDTO(pedidoActualizado);
    }

    @Override
    public void deletePedido(Integer id) {
        // 1. Verificamos que el registro exista antes de intentar borrarlo
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El pedido con ID " + id + " no existe."));
        
        // 2. Eliminamos el registro definitivamente
        pedidoRepository.delete(pedidoExistente);
    }
}