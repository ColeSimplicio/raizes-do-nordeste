package com.nicole.raizes_do_nordeste.application.dto.request;

import java.math.BigDecimal;

public record ItemCardapioRequest(
        BigDecimal preco,
        Boolean disponivel
) {}