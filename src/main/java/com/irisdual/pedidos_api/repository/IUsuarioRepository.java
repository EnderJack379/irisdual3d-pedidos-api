package com.irisdual.pedidos_api.repository;

import com.irisdual.pedidos_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    List<Usuario> findUsuariosByEmail(@Param("email") String email);

    default Optional<Usuario> findUsuarioByEmail(String email) {
        List<Usuario> usuarios = findUsuariosByEmail(email);
        return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
    }
}