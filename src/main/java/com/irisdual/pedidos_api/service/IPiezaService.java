package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.PiezaDTO;
import com.irisdual.pedidos_api.entity.Pieza;
import java.util.List;

public interface IPiezaService {
    List<PiezaDTO> getAllPiezas();
    List<PiezaDTO> getPiezasByCategoria(Integer categoriaId);
    
    Pieza createPieza(Pieza pieza);
    List<Pieza> createVariasPiezas(List<Pieza> piezas);
    Pieza updatePieza(Integer id, Pieza pieza);
    String deletePieza(Integer id);
}