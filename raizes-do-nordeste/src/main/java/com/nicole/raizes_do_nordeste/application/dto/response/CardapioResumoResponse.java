package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Cardapio;

public record CardapioResumoResponse(
        Long id,
        boolean disponivel
) {

    public CardapioResumoResponse(Cardapio cardapio) {
        this(
                cardapio.getId(),
                cardapio.isDisponivel()
        );
    }
}
