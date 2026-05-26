package com.nicole.raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nicole.raizes_do_nordeste.application.dto.request.PagamentoMockRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.PagamentoResponse;
import com.nicole.raizes_do_nordeste.application.service.PagamentoService;
import com.nicole.raizes_do_nordeste.domain.model.Pagamento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/pedido/{pedidoId}")
    @Transactional
    public ResponseEntity<PagamentoResponse> processarPagamento(
            @PathVariable Long pedidoId,
            @RequestBody @Valid PagamentoMockRequest dados
    ) {

        Pagamento pagamento =
                pagamentoService.processarPagamento(
                        pedidoId,
                        dados
                );

        return ResponseEntity.ok(
                new PagamentoResponse(pagamento)
        );
    }
}
