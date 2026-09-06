package com.irisdual.pedidos_api.controller;

import com.irisdual.pedidos_api.dto.PiezaDTO;
import com.irisdual.pedidos_api.entity.Pieza;
import com.irisdual.pedidos_api.service.IPiezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piezas")
public class PiezaController {

    @Autowired
    private IPiezaService piezaService;

    // Métodos GET actualizados para devolver PiezaDTO y evitar el error de carga perezosa (LAZY)
    @GetMapping
    public List<PiezaDTO> getAllPiezas() {
        return piezaService.getAllPiezas();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<PiezaDTO> getPiezasByCategoria(@PathVariable Integer categoriaId) {
        return piezaService.getPiezasByCategoria(categoriaId);
    }

    // Los métodos de escritura (POST, PUT, DELETE) siguen usando la entidad cruda por simplicidad
    @PostMapping
    public Pieza createPieza(@RequestBody Pieza pieza) {
        return piezaService.createPieza(pieza);
    }

    @PostMapping("/lote")
    public List<Pieza> createVariasPiezas(@RequestBody List<Pieza> piezas) {
        return piezaService.createVariasPiezas(piezas);
    }

    @PutMapping("/{id}")
    public Pieza updatePieza(@PathVariable Integer id, @RequestBody Pieza pieza) {
        return piezaService.updatePieza(id, pieza);
    }

    @DeleteMapping("/{id}")
    public String deletePieza(@PathVariable Integer id) {
        return piezaService.deletePieza(id);
    }
}