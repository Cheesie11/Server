package ch.bbw.ape.ipacriteriabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.bbw.ape.ipacriteriabackend.dto.AuthRequestDto;
import ch.bbw.ape.ipacriteriabackend.person.Person;
import ch.bbw.ape.ipacriteriabackend.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequestDto request) {
        try {
            authService.register(request.firstname(), request.lastname());
            return ResponseEntity.ok("Registration successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto request) {
        try {
            Person user = authService.login(request.firstname(), request.lastname());
            return ResponseEntity.ok(new LoginResponse(user.getId(), user.getFirstname(), user.getLastname()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

/**
 * DTO for login response
 */
record LoginResponse(String id, String firstname, String lastname) {}