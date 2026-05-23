package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.ItemCardapio;

import java.math.BigDecimal;

public record ItemCardapioResponse(
        Long id,
        BigDecimal preco,
        boolean disponivel,
        ProdutoResponse produto
) {
    public ItemCardapioResponse(ItemCardapio item) {
        this(
                item.getId(),
                item.getPreco(),
                item.isDisponivel(),
                new ProdutoResponse(item.getProduto())
        );
    }
}