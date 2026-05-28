package com.nicole.raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseValidacao(

        LocalDateTime timestamp,
        Integer status,
        String erro,
        List<String> mensagens,
        String path

) {
}