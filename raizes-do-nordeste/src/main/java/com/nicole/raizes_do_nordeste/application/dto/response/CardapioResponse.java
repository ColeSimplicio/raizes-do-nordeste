package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Cardapio;

import java.util.List;

public record CardapioResponse(
        Long id,
        boolean disponivel,
        List<ItemCardapioResponse> itens
) {
    public CardapioResponse(Cardapio cardapio) {
        this(
                cardapio.getId(),
                cardapio.isDisponivel(),
                cardapio.getItens()
                        .stream()
                        .map(ItemCardapioResponse::new)
                        .toList()
        );
    }
}