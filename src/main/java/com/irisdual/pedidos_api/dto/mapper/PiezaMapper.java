package com.irisdual.pedidos_api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.irisdual.pedidos_api.dto.PiezaDTO;
import com.irisdual.pedidos_api.entity.Pieza;

@Mapper(componentModel = "spring")
public interface PiezaMapper {

    // Le indicamos a MapStruct de dónde sacar la información para rellenar el campo "nombreCategoria" del DTO
    @Mapping(target = "nombreCategoria", source = "categoria.nombreCategoria")
    PiezaDTO piezaAPiezaDTO(Pieza pieza);
}