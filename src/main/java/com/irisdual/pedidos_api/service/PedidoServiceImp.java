package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.dto.mapper.PedidoMapper;
import com.irisdual.pedidos_api.entity.Pedido;
import com.irisdual.pedidos_api.entity.Pieza;
import com.irisdual.pedidos_api.repository.PedidoRepository;
import com.irisdual.pedidos_api.repository.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Import agregado

import java.util.ArrayList;
import java.util.List;

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
}