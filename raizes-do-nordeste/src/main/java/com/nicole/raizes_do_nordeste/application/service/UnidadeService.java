package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.UnidadeRequest;
import com.nicole.raizes_do_nordeste.domain.model.Cardapio;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import com.nicole.raizes_do_nordeste.repository.CardapioRepository;
import com.nicole.raizes_do_nordeste.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {
    @Autowired
    UnidadeRepository repository;
    @Autowired
    CardapioRepository cardapioRepository;

    public Unidade criarUnidade(UnidadeRequest dados){
        Unidade unidade = new Unidade(dados);
        Cardapio cardapio = new Cardapio(unidade);
        cardapioRepository.save(cardapio);
        return repository.save(unidade);
    }

    public void removerUnidade(Long id){
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        repository.deleteById(id);
    }

    public List<Unidade> listarUnidades(){
        return repository.findAll();
    }

    public Unidade editarUnidade(Long id, UnidadeRequest dados){
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        unidade.atualizarDados(dados);
        return repository.save(unidade);
    }

}
