package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CriarPedidoRequest(

        @NotNull(message = "Unidade é obrigatória")
        Long unidadeId,

        @NotNull(message = "Canal do pedido é obrigatório")
        CanalPedido canalPedido,

        @NotNull(message = "Item é obrigatório")
        List<ItemPedidoRequest> itens,

        @NotNull(message = "Método de pagamento é obrigatório")
        MetodoPagamento metodoPagamento,
        Integer pontosUtilizados

) {
}
