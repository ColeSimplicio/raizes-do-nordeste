package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Estoque;

public record EstoqueResponse(
        Long id,
        Integer quantidade,
        Long unidadeId,
        ProdutoResponse produto
) {
    public EstoqueResponse(Estoque estoque) {
        this(
                estoque.getId(),
                estoque.getQuantidade(),
                estoque.getUnidade().getId(),
                new ProdutoResponse(estoque.getProduto())
        );
    }
}