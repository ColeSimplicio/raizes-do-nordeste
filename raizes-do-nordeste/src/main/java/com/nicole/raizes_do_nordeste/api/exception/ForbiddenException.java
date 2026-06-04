package com.nicole.raizes_do_nordeste.api.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String mensagem) {
        super(mensagem);
    }
}
