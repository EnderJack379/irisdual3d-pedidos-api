package com.irisdual.pedidos_api.service;

import com.irisdual.pedidos_api.dto.auth.AuthRequest;
import com.irisdual.pedidos_api.dto.auth.AuthResponse;
import com.irisdual.pedidos_api.dto.auth.RegisterRequest;
import com.irisdual.pedidos_api.entity.Usuario;
import com.irisdual.pedidos_api.repository.IUsuarioRepository;
import com.irisdual.pedidos_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUsuarioRepository repository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = new Usuario();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        Usuario user = repository.findUsuarioByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
