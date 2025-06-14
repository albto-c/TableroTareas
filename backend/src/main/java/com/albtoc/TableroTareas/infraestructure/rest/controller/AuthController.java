package com.albtoc.TableroTareas.infraestructure.rest.controller;

import com.albtoc.TableroTareas.application.ports.in.AuthService;
import com.albtoc.TableroTareas.domain.dtos.LoginDto;
import com.albtoc.TableroTareas.domain.exceptions.IncorrectPasswordException;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws UserNotFoundException, IncorrectPasswordException {
        String token = authService.login(loginDto);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginDto loginDto) {
        return authService.register(loginDto) ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
