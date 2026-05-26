package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import com.nicole.raizes_do_nordeste.domain.model.Pagamento;

public record PagamentoResponse(

        Long id,
        StatusPagamento statusPagamento

) {

    public PagamentoResponse(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getStatusPagamento()
        );
    }
}
