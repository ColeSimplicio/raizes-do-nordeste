package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.EstoqueRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.EstoqueResponse;
import com.nicole.raizes_do_nordeste.application.service.EstoqueService;
import com.nicole.raizes_do_nordeste.domain.model.Estoque;
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
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/unidades/{unidadeId}/produtos/{produtoId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstoqueResponse> adicionarProduto(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId,
            @RequestBody @Valid EstoqueRequest dados,
            UriComponentsBuilder uriBuilder
    ) {

        Estoque estoque = estoqueService.adicionarProdutoNoEstoque(
                unidadeId,
                produtoId,
                dados
        );

        var uri = uriBuilder
                .path("/estoque/unidades/{unidadeId}/produtos/{produtoId}")
                .buildAndExpand(unidadeId, produtoId)
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new EstoqueResponse(estoque));
    }

    @PutMapping("/unidades/{unidadeId}/produtos/{produtoId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstoqueResponse> atualizarQuantidade(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId,
            @RequestBody @Valid EstoqueRequest dados
    ) {

        Estoque estoque = estoqueService.atualizarQuantidade(
                unidadeId,
                produtoId,
                dados.quantidade()
        );

        return ResponseEntity.ok(
                new EstoqueResponse(estoque)
        );
    }

    @DeleteMapping("/unidades/{unidadeId}/produtos/{produtoId}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerProduto(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId
    ) {

        estoqueService.removerProdutoDoEstoque(
                unidadeId,
                produtoId
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unidades/{unidadeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EstoqueResponse>> listar(
            @PathVariable Long unidadeId,
            Pageable pageable
    ) {

        Page<EstoqueResponse> estoques =
                estoqueService.listarEstoqueDaUnidade(
                        unidadeId,
                        pageable
                );

        return ResponseEntity.ok(estoques);
    }
}