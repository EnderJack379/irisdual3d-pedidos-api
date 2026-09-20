package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.PiezaDTO;
import com.irisdual.pedidos_api.dto.mapper.PiezaMapper;
import com.irisdual.pedidos_api.entity.Categoria;
import com.irisdual.pedidos_api.entity.Pieza;
import com.irisdual.pedidos_api.repository.CategoriaRepository;
import com.irisdual.pedidos_api.repository.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiezaServiceImp implements IPiezaService {

    @Autowired
    private PiezaRepository piezaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PiezaMapper piezaMapper; // Inyectamos el Mapper generado por MapStruct

    @Override
    public List<PiezaDTO> getAllPiezas() {
        return piezaRepository.findAll().stream()
                .map(pieza -> piezaMapper.piezaAPiezaDTO(pieza))
                .toList();
    }

    @Override
    public List<PiezaDTO> getPiezasByCategoria(Integer categoriaId) {
        return piezaRepository.findByCategoriaId(categoriaId).stream()
                .map(pieza -> piezaMapper.piezaAPiezaDTO(pieza))
                .toList();
    }

    @Override
    public Pieza createPieza(Pieza pieza) {
        Categoria categoriaReal = categoriaRepository.findById(pieza.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Error: La categoría no existe"));
        pieza.setCategoria(categoriaReal);
        return piezaRepository.save(pieza);
    }

    @Override
    public List<Pieza> createVariasPiezas(List<Pieza> piezas) {
        for (Pieza pieza : piezas) {
            Categoria categoriaReal = categoriaRepository.findById(pieza.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Error: La categoría no existe"));
            pieza.setCategoria(categoriaReal);
        }
        return piezaRepository.saveAll(piezas);
    }

    @Override
    public Pieza updatePieza(Integer id, Pieza piezaActualizada) {
        return piezaRepository.findById(id).map(pieza -> {
            pieza.setNombre(piezaActualizada.getNombre());
            pieza.setPrecio(piezaActualizada.getPrecio());
            pieza.setStock(piezaActualizada.getStock());
            
            // Verificamos si también quisieron cambiarle la categoría
            if (piezaActualizada.getCategoria() != null && piezaActualizada.getCategoria().getId() != null) {
                Categoria categoriaReal = categoriaRepository.findById(piezaActualizada.getCategoria().getId())
                        .orElseThrow(() -> new RuntimeException("Error: La categoría no existe"));
                pieza.setCategoria(categoriaReal);
            }
            
            return piezaRepository.save(pieza);
        }).orElseThrow(() -> new RuntimeException("Error: La pieza no existe"));
    }

    @Override
    public String deletePieza(Integer id) {
        piezaRepository.deleteById(id);
        return "¡Pieza eliminada de la base de datos!";
    }
}