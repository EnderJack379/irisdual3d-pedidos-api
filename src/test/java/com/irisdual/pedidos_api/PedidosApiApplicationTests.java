package com.irisdual.pedidos_api;

import com.irisdual.pedidos_api.repository.PedidoRepository;
import com.irisdual.pedidos_api.service.IPedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PedidosApiApplicationTests {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private IPedidoService pedidoService;

    @Test
    void verificarInyeccionRepositorio() {
        assertNotNull(pedidoRepository, "El repositorio de pedidos debe estar instanciado en el contexto");
    }

    @Test
    void verificarConsultasPedido() {
        // Valida que la consulta a la base de datos y el mapeo a DTO se ejecuten sin lanzar excepciones de Hibernate
        assertDoesNotThrow(() -> pedidoService.getAllPedidos(), "La consulta general de pedidos no debe arrojar errores");
    }
}