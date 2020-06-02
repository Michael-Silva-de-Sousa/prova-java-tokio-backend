package com.example.api.exceptions;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(){
        super("Senha inválida");
    }
}
