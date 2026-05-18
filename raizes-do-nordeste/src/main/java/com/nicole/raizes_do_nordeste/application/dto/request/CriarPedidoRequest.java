package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.ItemPedido;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CriarPedidoRequest(@NotNull LocalDateTime horaPedido, @NotNull BigDecimal valorPedido, @NotNull BigDecimal desconto, @NotNull BigDecimal valorTotal, @NotNull
                                 CanalPedido canalPedido, @NotNull StatusPedido statusPedido, @NotNull List<ItemPedido> itens) {
}
