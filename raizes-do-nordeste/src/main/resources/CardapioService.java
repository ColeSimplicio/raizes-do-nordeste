package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardapioService {
    //criar cardapio, adicionar e remover produto
    @Autowired
    private CardapioRepository repository;


}
