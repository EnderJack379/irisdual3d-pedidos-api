package com.irisdual.pedidos_api.dto.mapper;

import com.irisdual.pedidos_api.dto.PedidoDTO;
import com.irisdual.pedidos_api.entity.Pedido;
import org.mapstruct.Mapper;

// El parámetro "uses" le dice a MapStruct cómo convertir las piezas de adentro
@Mapper(componentModel = "spring", uses = {PiezaMapper.class})
public interface PedidoMapper {
    
    PedidoDTO pedidoAPedidoDTO(Pedido pedido);
}
