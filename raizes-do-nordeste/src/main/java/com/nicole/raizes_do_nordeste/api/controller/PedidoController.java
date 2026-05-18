package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.CriarPedidoRequest;
import com.nicole.raizes_do_nordeste.domain.model.Pedido;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    // criar pedido, status, consulta
    @PostMapping
    @Transactional
    public ResponseEntity criarPedido(@RequestBody @Valid CriarPedidoRequest dados){
        Pedido pedido = new Pedido(dados);

        return ResponseEntity.ok(pedido);
    }

}
