package com.nicole.raizes_do_nordeste.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemCardapioEdicaoRequest(BigDecimal preco, Boolean disponivel, LocalDate dataInicio, LocalDate dataFim, Boolean sazonal) {
}
