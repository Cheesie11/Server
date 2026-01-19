package ch.bbw.ape.ipacriteriabackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ch.bbw.ape.ipacriteriabackend.dto.AuthRequestDto;
import ch.bbw.ape.ipacriteriabackend.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthService authService;

    AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
    }

    @Test
    void register_success() {
        AuthRequestDto request = new AuthRequestDto("Max", "Mustermann");

        doNothing().when(authService).register("Max", "Mustermann");

        ResponseEntity<?> response = authController.register(request);

        assertEquals("Registration successful", response.getBody());
    }

    @Test
    void login_success() {
        AuthRequestDto request = new AuthRequestDto("Max", "Mustermann");

        when(authService.login("Max", "Mustermann")).thenReturn(true);

        ResponseEntity<?> response = authController.login(request);

        assertEquals("Login successful", response.getBody());
    }

}
