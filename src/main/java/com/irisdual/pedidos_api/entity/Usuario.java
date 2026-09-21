package com.irisdual.pedidos_api.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> roles;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }

    // --- Métodos de UserDetails ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombreRol()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
