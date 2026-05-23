package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.EstoqueRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.EstoqueResponse;
import com.nicole.raizes_do_nordeste.application.service.EstoqueService;
import com.nicole.raizes_do_nordeste.domain.model.Estoque;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/unidades/{unidadeId}/produtos/{produtoId}")
    @Transactional
    public ResponseEntity<Void> adicionarProduto(
            @PathVariable Long unidadeId,
            @PathVariable Long produtoId,
            @RequestBody EstoqueRequest dados
    ) {

        estoqueService.adicionarProdutoNoEstoque(unidadeId, produtoId, dados);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/entrada")
    @Transactional
    public ResponseEntity<Void> entrada(
            @RequestParam Long unidadeId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade
    ) {

        estoqueService.entradaEstoque(unidadeId, produtoId, quantidade);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/saida")
    @Transactional
    public ResponseEntity<Void> saida(
            @RequestParam Long unidadeId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade
    ) {

        estoqueService.saidaEstoque(unidadeId, produtoId, quantidade);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(
            @RequestParam Long unidadeId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade
    ) {

        estoqueService.atualizarQuantidade(unidadeId, produtoId, quantidade);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/unidades/{unidadeId}")
    public ResponseEntity<List<Estoque>> listar(
            @PathVariable Long unidadeId
    ) {

        return ResponseEntity.ok(
                estoqueService.listarEstoqueDaUnidade(unidadeId)
        );
    }
}