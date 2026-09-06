package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.entity.Pedido;
import java.util.List;

public interface IPedidoService {
    List<PedidoDTO> getAllPedidos();
    PedidoDTO createPedido(Pedido pedido); // <- Cambio aquí
}