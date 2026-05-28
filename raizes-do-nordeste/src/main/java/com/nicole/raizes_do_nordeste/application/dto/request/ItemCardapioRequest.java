package com.nicole.raizes_do_nordeste.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemCardapioRequest(
        Boolean sazonal,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal preco,
        Boolean disponivel
) {}