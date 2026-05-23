package com.nicole.raizes_do_nordeste.api.controller;

import com.nicole.raizes_do_nordeste.application.dto.request.UnidadeRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.UnidadeResponse;
import com.nicole.raizes_do_nordeste.application.service.UnidadeService;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {
    @Autowired
    UnidadeService service;
    @PostMapping
    @Transactional
    public ResponseEntity<UnidadeResponse> criarUnidade(@RequestBody @Valid UnidadeRequest dados, UriComponentsBuilder uriBuilder){
        Unidade unidade = service.criarUnidade(dados);

        var uri = uriBuilder
                .path("/unidades/{id}")
                .buildAndExpand(unidade.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UnidadeResponse(unidade));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removerUnidade(@PathVariable Long id){
        service.removerUnidade(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UnidadeResponse> editarUnidade(@PathVariable Long id, @RequestBody @Valid UnidadeRequest dados){
        Unidade unidade = service.editarUnidade(id, dados);

        return ResponseEntity.ok(new UnidadeResponse(unidade));
    }

    @GetMapping
    public ResponseEntity<Page<UnidadeResponse>> listarUnidades(
            Pageable pageable
    ){

        Page<UnidadeResponse> unidades =
                service.listarUnidades(pageable);

        return ResponseEntity.ok(unidades);
    }
}
