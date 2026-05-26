package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CriarPedidoRequest(

        @NotNull
        Long usuarioId,

        @NotNull
        Long unidadeId,

        @NotNull
        CanalPedido canalPedido,

        @NotNull
        List<ItemPedidoRequest> itens,

        @NotNull
        MetodoPagamento metodoPagamento

) {
}
