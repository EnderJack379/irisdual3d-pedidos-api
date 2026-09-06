package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.dto.mapper.PedidoMapper;
import com.irisdual.pedidos_api.entity.Pedido;
import com.irisdual.pedidos_api.entity.Pieza;
import com.irisdual.pedidos_api.repository.PedidoRepository;
import com.irisdual.pedidos_api.repository.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
                .collect(Collectors.toList());
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
}