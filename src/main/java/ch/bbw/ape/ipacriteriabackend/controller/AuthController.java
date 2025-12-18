package ch.bbw.ape.ipacriteriabackend.controller;

import ch.bbw.ape.ipacriteriabackend.service.AuthService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record AuthRequest(
            @NotBlank String username,
            @NotBlank String password
    ) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        try {
            authService.register(request.username(), request.password());
            return ResponseEntity.ok("Registration successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        boolean success = authService.login(request.username(), request.password());
        if (success) return ResponseEntity.ok("Login successful");
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}