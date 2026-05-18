package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.UnidadeRequest;
import com.nicole.raizes_do_nordeste.application.service.UnidadeService;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {
    @Autowired
    UnidadeService service;
    @PostMapping
    @Transactional
    public ResponseEntity criarUnidade(@RequestBody @Valid UnidadeRequest dados, UriComponentsBuilder uriBuilder){
        Unidade unidade = service.criarUnidade(dados);

        var uri = uriBuilder
                .path("/unidades/{id}")
                .buildAndExpand(unidade.getId())
                .toUri();

        return ResponseEntity.created(uri).body(unidade);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removerUnidade(@PathVariable Long id){
        service.removerUnidade(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity editarUnidade(@PathVariable Long id, @RequestBody @Valid UnidadeRequest dados){
        Unidade unidade = service.editarUnidade(id, dados);

        return ResponseEntity.ok(unidade);
    }

    @GetMapping
    public ResponseEntity listarUnidades(){
        List<Unidade> unidadeList = service.listarUnidades();

        return unidadeList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(unidadeList);
    }
}
