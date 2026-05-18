package com.nicole.raizes_do_nordeste.infrastructure.exception;

import com.nicole.raizes_do_nordeste.domain.exception.EmailDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<String> tratarEmailDuplicado(
            EmailDuplicadoException ex
    ){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
