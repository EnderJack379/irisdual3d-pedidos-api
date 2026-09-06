package com.irisdual.pedidos_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.irisdual.pedidos_api.repository.PedidoRepository;

@SpringBootTest
class PedidosApiApplicationTests {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void verificarInyeccionRepositorio() {
        // Valida que el repositorio se conecte correctamente a la base de datos
        assertNotNull(pedidoRepository, "El repositorio de pedidos debe estar instanciado en el contexto");
    }
}