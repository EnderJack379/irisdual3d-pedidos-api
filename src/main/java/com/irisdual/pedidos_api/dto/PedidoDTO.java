package com.irisdual.pedidos_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Integer id;
    private String nombreCliente;
    private LocalDateTime fechaPedido;
    private List<PiezaDTO> piezas; // ¡Reutilizamos tu DTO anterior!

    // Getters y Setters explícitos
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public List<PiezaDTO> getPiezas() { return piezas; }
    public void setPiezas(List<PiezaDTO> piezas) { this.piezas = piezas; }
}