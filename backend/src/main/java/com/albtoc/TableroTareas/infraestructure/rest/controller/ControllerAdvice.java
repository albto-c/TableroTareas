package com.albtoc.TableroTareas.infraestructure.rest.controller;

import com.albtoc.TableroTareas.domain.exceptions.HeaderAuthorizationException;
import com.albtoc.TableroTareas.domain.exceptions.IncorrectPasswordException;
import com.albtoc.TableroTareas.domain.exceptions.TokenException;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> incorrectPassword() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HeaderAuthorizationException.class)
    public ResponseEntity<?> headerAuthorizationException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<?> tokenException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
