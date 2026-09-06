package com.irisdual.pedidos_api.dto;

public class PiezaDTO {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String nombreCategoria;

    // Getters y Setters explícitos para que MapStruct los encuentre sí o sí
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
}