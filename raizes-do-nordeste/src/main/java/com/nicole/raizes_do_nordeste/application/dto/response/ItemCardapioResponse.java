package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.ItemCardapio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemCardapioResponse(
        Long id,
        BigDecimal preco,
        boolean disponivel,
        boolean sazonal,
        LocalDate dataInicio,
        LocalDate dataFim,
        ProdutoResponse produto
) {
    public ItemCardapioResponse(ItemCardapio item) {
        this(
                item.getId(),
                item.getPreco(),
                item.isDisponivel(),
                item.isSazonal(),
                item.getDataInicio(),
                item.getDataFim(),
                new ProdutoResponse(item.getProduto())
        );
    }
}