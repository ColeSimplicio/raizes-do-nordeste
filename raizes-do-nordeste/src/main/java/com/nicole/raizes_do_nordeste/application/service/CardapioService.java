package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioPrecoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.ItemCardapioResponse;
import com.nicole.raizes_do_nordeste.domain.model.Cardapio;
import com.nicole.raizes_do_nordeste.domain.model.ItemCardapio;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import com.nicole.raizes_do_nordeste.repository.ItemCardapioRepository;
import com.nicole.raizes_do_nordeste.repository.ProdutoRepository;
import com.nicole.raizes_do_nordeste.repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardapioService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    private Cardapio buscarCardapioDaUnidade(Long unidadeId){
        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        if(unidade.getCardapio() == null){
            throw new RuntimeException("Cardápio não encontrado");
        }
        return unidade.getCardapio();
    }

    @Transactional
    public ItemCardapio adicionarItem(Long unidadeId, Long produtoId, ItemCardapioRequest dados){
        Cardapio cardapio = buscarCardapioDaUnidade(unidadeId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        boolean produtoJaExiste = cardapio.getItens()
                .stream()
                .anyMatch(item -> item.getProduto().getId().equals(produtoId));

        if(produtoJaExiste){
            throw new RuntimeException("Produto já existe no cardápio");
        }

        ItemCardapio item = new ItemCardapio(dados, cardapio, produto);

        cardapio.adicionarItem(item);

        return itemCardapioRepository.save(item);
    }

    @Transactional
    public void removerItem(Long unidadeId, Long itemId){

        Cardapio cardapio = buscarCardapioDaUnidade(unidadeId);

        ItemCardapio item = itemCardapioRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if(!item.getCardapio().getId().equals(cardapio.getId())){
            throw new RuntimeException("Esse item não pertence a esse cardápio");
        }

        cardapio.removerItem(item);

        itemCardapioRepository.delete(item);
    }

    @Transactional
    public ItemCardapio editarItem(Long unidadeId, Long itemId, ItemCardapioPrecoRequest dados){
        Cardapio cardapio = buscarCardapioDaUnidade(unidadeId);

        ItemCardapio item = itemCardapioRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if(!item.getCardapio().getId().equals(cardapio.getId())){
            throw new RuntimeException("Esse item não pertence a esse cardápio");
        }

        item.editarPreco(dados);

        return itemCardapioRepository.save(item);
    }

    public Page<ItemCardapioResponse> listarItensPorCardapio(
            Long unidadeId,
            Pageable pageable
    ){

        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        Cardapio cardapio = unidade.getCardapio();

        return itemCardapioRepository
                .findByCardapioId(cardapio.getId(), pageable)
                .map(ItemCardapioResponse::new);
    }
}