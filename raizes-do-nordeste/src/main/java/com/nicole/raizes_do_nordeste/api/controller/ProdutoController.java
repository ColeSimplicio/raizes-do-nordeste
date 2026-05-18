package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.ProdutoRequest;
import com.nicole.raizes_do_nordeste.application.service.ProdutoService;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    // CRUD, produtos disponíveis, consulta
    @Autowired
    ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity adicionarProduto(@RequestBody @Valid ProdutoRequest dados, UriComponentsBuilder uriBuilder){
        Produto produto = produtoService.criarProduto(dados);

        var uri = uriBuilder
                .path("/produtos/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity editarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequest dados
    ) {
        Produto produto = produtoService.editarProduto(dados, id);

        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity listarProdutos() {
        List<Produto> produtoList = produtoService.listarProdutos();

        return produtoList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(produtoList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarProduto(@PathVariable Long id){
        produtoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }

}
