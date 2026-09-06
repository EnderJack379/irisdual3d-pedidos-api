package com.irisdual.pedidos_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irisdual.pedidos_api.entity.Pieza;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Integer> {
    
    // Spring Boot es tan inteligente que si nombramos el método así,
    // él mismo arma la consulta SQL por detrás (SELECT * FROM piezas WHERE cat_id = ?)
    List<Pieza> findByCategoriaId(Integer categoriaId);
}