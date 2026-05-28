package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.CriarPedidoRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.PedidoResponse;
import com.nicole.raizes_do_nordeste.application.dto.response.PedidoStatusResponse;
import com.nicole.raizes_do_nordeste.application.service.PedidoService;
import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.model.Pedido;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Transactional
    public ResponseEntity<PedidoResponse> criarPedido(
            @RequestBody @Valid CriarPedidoRequest dados,
            UriComponentsBuilder uriBuilder
    ) {

        Pedido pedido = pedidoService.criarPedido(dados);

        var uri = uriBuilder
                .path("/pedidos/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new PedidoResponse(pedido));
    }

    @PutMapping("/{id}/cancelamento")
    @Transactional
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable Long id
    ) {

        pedidoService.cancelarPedido(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(
            @PathVariable Long id
    ) {

        Pedido pedido = pedidoService.buscarPedido(id);

        return ResponseEntity.ok(
                new PedidoResponse(pedido)
        );
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<PedidoStatusResponse> consultarStatus(
            @PathVariable Long id
    ) {

        Pedido pedido = pedidoService.buscarPedido(id);

        return ResponseEntity.ok(
                new PedidoStatusResponse(pedido)
        );
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> listarPedidos(
            @RequestParam(required = false)
            CanalPedido canalPedido,

            Pageable pageable
    ) {

        return ResponseEntity.ok(
                pedidoService.listarPedidos(
                        canalPedido,
                        pageable
                )
        );
    }

    /// query por canal de pedido
}