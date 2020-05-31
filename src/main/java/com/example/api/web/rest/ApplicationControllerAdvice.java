package com.example.api.web.rest;

import com.example.api.resonse.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> validException(MethodArgumentNotValidException ex) {
        Response<String> response = new Response<String>();
        List<String> erros = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        response.getErrors().addAll(erros);
        return ResponseEntity.badRequest().body(response);
    }
}
