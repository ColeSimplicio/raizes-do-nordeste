package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.nicole.raizes_do_nordeste.application.dto.request.UnidadeRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.UnidadeResponse;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import com.nicole.raizes_do_nordeste.infrastructure.repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {
    @Autowired
    UnidadeRepository repository;
    @Transactional
    public Unidade criarUnidade(UnidadeRequest dados) {
        Unidade unidade = new Unidade(dados);
        return repository.save(unidade);
    }
    @Transactional
    public void removerUnidade(Long id){
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));
        repository.deleteById(id);
    }

    public Page<UnidadeResponse> listarUnidades(Pageable pageable){

        return repository.findAll(pageable)
                .map(UnidadeResponse::new);
    }
    @Transactional
    public Unidade editarUnidade(Long id, UnidadeRequest dados){
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));
        unidade.atualizarDados(dados);
        return repository.save(unidade);
    }

}
