package com.irisdual.pedidos_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_id")
    private Integer id;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    // Relación Muchos a Muchos con la tabla intermedia
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pedidos_piezas",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "pieza_id")
    )
    private List<Pieza> piezas;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public List<Pieza> getPiezas() { return piezas; }
    public void setPiezas(List<Pieza> piezas) { this.piezas = piezas; }

    // Método para auto-asignar la fecha al momento de crearlo
    @PrePersist
    public void prePersist() {
        this.fechaPedido = LocalDateTime.now();
    }
}