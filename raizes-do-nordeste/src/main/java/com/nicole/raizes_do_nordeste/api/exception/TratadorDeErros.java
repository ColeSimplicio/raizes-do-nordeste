package com.nicole.raizes_do_nordeste.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request
    ) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(
            RegraNegocioException ex,
            HttpServletRequest request
    ) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de negócio",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseValidacao> tratarValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<String> erros = ex.getFieldErrors()
                .stream()
                .map(erro ->
                        erro.getField()
                                + ": "
                                + erro.getDefaultMessage())
                .toList();

        ErroResponseValidacao response =
                new ErroResponseValidacao(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro de validação",
                        erros,
                        request.getRequestURI()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "JSON inválido",
                "Valor inválido enviado no corpo da requisição",
                request.getRequestURI()
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErroResponse> tratarUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Não autorizado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
}
