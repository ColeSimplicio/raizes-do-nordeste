package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioEdicaoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.ItemCardapioResponse;
import com.nicole.raizes_do_nordeste.application.service.CardapioService;
import com.nicole.raizes_do_nordeste.domain.model.ItemCardapio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/unidades/{unidadeId}/cardapio")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @PostMapping("/produtos/{produtoId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemCardapioResponse> adicionarItem(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId,
            @RequestBody @Valid ItemCardapioRequest dados,
            UriComponentsBuilder uriBuilder
    ) {

        ItemCardapio item = cardapioService.adicionarItem(unidadeId, produtoId, dados);

        var uri = uriBuilder
                .path("/unidades/{unidadeId}/cardapio/{itemId}")
                .buildAndExpand(unidadeId, item.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ItemCardapioResponse(item));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<Page<ItemCardapioResponse>> listarItensCardapio(
            @PathVariable Long unidadeId,
            Pageable pageable
    ) {

        Page<ItemCardapioResponse> itens =
                cardapioService.listarItensPorCardapio(
                        unidadeId,
                        pageable
                );

        return ResponseEntity.ok(itens);
    }

    @DeleteMapping("/{itemId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerItem(
            @PathVariable Long unidadeId,
            @PathVariable Long itemId
    ) {

        cardapioService.removerItem(unidadeId, itemId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemCardapioResponse> editar(
            @PathVariable Long unidadeId,
            @PathVariable Long itemId,
            @RequestBody @Valid ItemCardapioEdicaoRequest dados
    ) {

        ItemCardapio item =
                cardapioService.editar(unidadeId, itemId, dados);

        return ResponseEntity.ok(new ItemCardapioResponse(item));
    }
}