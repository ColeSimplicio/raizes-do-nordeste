package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.Pedido;

public record PedidoStatusResponse(
        Long id,
        StatusPedido statusPedido,
        StatusPagamento statusPagamento
) {

    public PedidoStatusResponse(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getStatusPedido(),
                pedido.getPagamento().getStatusPagamento()
        );
    }
}
