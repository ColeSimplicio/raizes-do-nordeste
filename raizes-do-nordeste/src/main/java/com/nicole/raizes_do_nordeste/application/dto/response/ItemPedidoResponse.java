package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario
) {

    public ItemPedidoResponse(ItemPedido item) {
        this(
                item.getProduto().getId(),
                item.getProduto().getNomeProduto(),
                item.getQuantidade(),
                item.getPrecoUnitario()
        );
    }
}