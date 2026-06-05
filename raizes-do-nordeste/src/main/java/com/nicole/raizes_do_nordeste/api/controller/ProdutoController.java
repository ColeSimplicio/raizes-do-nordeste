package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.ProdutoRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.ProdutoResponse;
import com.nicole.raizes_do_nordeste.application.service.ProdutoService;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> adicionarProduto(@RequestBody @Valid ProdutoRequest dados, UriComponentsBuilder uriBuilder){
        Produto produto = produtoService.criarProduto(dados);

        var uri = uriBuilder
                .path("/produtos/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> editarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequest dados
    ) {
        Produto produto = produtoService.editarProduto(dados, id);

        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity listarProdutos(Pageable pageable) {
        Page<ProdutoResponse> produtos =
                produtoService.listarProdutos(pageable);

        return ResponseEntity.ok(produtos);

    }

}
