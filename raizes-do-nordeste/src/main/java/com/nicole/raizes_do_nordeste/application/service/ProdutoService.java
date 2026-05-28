
package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.nicole.raizes_do_nordeste.application.dto.request.ProdutoRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.ProdutoResponse;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import com.nicole.raizes_do_nordeste.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;
    @Transactional
    public Produto criarProduto(ProdutoRequest dados){

        Produto produto = new Produto(dados);

        return repository.save(produto);
    }

    public void removerProduto(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
        repository.delete(produto);
    }

    public Produto editarProduto(ProdutoRequest dados, Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        produto.atualizarDados(dados);

        return repository.save(produto);
    }


    public Page<ProdutoResponse> listarProdutos(
            Pageable pageable
    ){

        return repository.findAll(pageable)
                .map(ProdutoResponse::new);
    }
}
