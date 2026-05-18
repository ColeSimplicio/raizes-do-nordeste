package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.model.Pedido;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoRequest(@NotNull Integer quantidade, @NotNull BigDecimal precoUnitario, @NotNull Produto produto, @NotNull
                                Pedido pedido) {
}
