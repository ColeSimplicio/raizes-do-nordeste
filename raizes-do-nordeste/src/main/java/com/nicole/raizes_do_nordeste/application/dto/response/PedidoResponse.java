package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        StatusPedido statusPedido,
        CanalPedido canalPedido,
        LocalDateTime horaPedido,
        BigDecimal valorPedido,
        BigDecimal descontoPedido,
        BigDecimal valorTotal,
        Long unidadeId,
        UsuarioResponse usuario,
        List<ItemPedidoResponse> itens
) {
    public PedidoResponse(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getStatusPedido(),
                pedido.getCanalPedido(),
                pedido.getHoraPedido(),
                pedido.getValorPedido(),
                pedido.getDescontoPedido(),
                pedido.getValorTotal(),
                pedido.getUnidade().getId(),
                new UsuarioResponse(pedido.getUsuario()),
                pedido.getItens()
                        .stream()
                        .map(ItemPedidoResponse::new)
                        .toList()
        );
    }
}