package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        ProdutoResponse produto
) {
    public ItemPedidoResponse(ItemPedido item) {
        this(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.calcularSubtotal(),
                new ProdutoResponse(item.getProduto())
        );
    }
}