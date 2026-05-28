package com.nicole.raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;

public record ErroResponse(

        LocalDateTime timestamp,
        Integer status,
        String erro,
        String mensagem,
        String path

) {
}