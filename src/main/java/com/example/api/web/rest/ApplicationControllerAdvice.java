package com.example.api.web.rest;

import com.example.api.exceptions.PasswordInvalidException;
import com.example.api.resonse.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Response<String> response = new Response<>();
        List<String> erros = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        response.getErrors().addAll(erros);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<String>> handleResponseStatusException(ResponseStatusException ex) {
        Response<String> response = new Response<>();
        String message = ex.getMessage();
        response.getErrors().add(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<Response<String>> handlePasswordInvalidException(PasswordInvalidException ex) {
        Response<String> response = new Response<>();
        String message = ex.getMessage();
        response.getErrors().add(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
