package com.irisdual.pedidos_api.repository;

import com.irisdual.pedidos_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByEmail(String email);
}
