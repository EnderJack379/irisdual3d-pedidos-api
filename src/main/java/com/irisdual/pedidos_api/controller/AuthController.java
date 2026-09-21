package com.irisdual.pedidos_api.controller;

import com.irisdual.pedidos_api.dto.auth.AuthRequest;
import com.irisdual.pedidos_api.dto.auth.AuthResponse;
import com.irisdual.pedidos_api.dto.auth.RegisterRequest;
import com.irisdual.pedidos_api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
