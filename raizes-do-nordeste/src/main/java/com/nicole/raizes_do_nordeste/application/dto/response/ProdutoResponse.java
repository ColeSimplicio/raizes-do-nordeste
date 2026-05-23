package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Produto;

public record ProdutoResponse(
        Long id,
        String nomeProduto
) {
    public ProdutoResponse(Produto produto) {
        this(
                produto.getId(),
                produto.getNomeProduto()
        );
    }
}