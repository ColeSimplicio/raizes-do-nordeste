package com.nicole.raizes_do_nordeste.domain.exception;

public class EmailDuplicadoException extends RuntimeException{
    public EmailDuplicadoException() {
        super("Email já cadastrado");
    }
}
