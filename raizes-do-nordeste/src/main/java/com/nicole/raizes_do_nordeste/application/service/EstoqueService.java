package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.EstoqueRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.EstoqueResponse;
import com.nicole.raizes_do_nordeste.domain.model.Estoque;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import com.nicole.raizes_do_nordeste.repository.EstoqueRepository;
import com.nicole.raizes_do_nordeste.repository.ProdutoRepository;
import com.nicole.raizes_do_nordeste.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public Estoque adicionarProdutoNoEstoque(
            Long idUnidade,
            Long idProduto,
            EstoqueRequest dados
    ) {

        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        boolean produtoJaExiste =
                estoqueRepository
                        .findByUnidadeIdAndProdutoId(idUnidade, idProduto)
                        .isPresent();

        if (produtoJaExiste) {
            throw new RuntimeException(
                    "Produto já existe no estoque"
            );
        }

        Estoque estoque =
                new Estoque(dados, unidade, produto);

        return estoqueRepository.save(estoque);
    }

    public Estoque atualizarQuantidade(
            Long idUnidade,
            Long idProduto,
            Integer novaQuantidade
    ) {

        Estoque estoque =
                buscarEstoque(idUnidade, idProduto);

        if (novaQuantidade < 0) {
            throw new RuntimeException(
                    "Quantidade não pode ser negativa"
            );
        }

        estoque.setQuantidade(novaQuantidade);

        return estoqueRepository.save(estoque);
    }

    public void removerProdutoDoEstoque(
            Long idUnidade,
            Long idProduto
    ) {

        Estoque estoque =
                buscarEstoque(idUnidade, idProduto);

        estoqueRepository.delete(estoque);
    }

    public Page<EstoqueResponse> listarEstoqueDaUnidade(
            Long idUnidade,
            Pageable pageable
    ) {

        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        return estoqueRepository
                .findByUnidadeId(unidade.getId(), pageable)
                .map(EstoqueResponse::new);
    }

    private Estoque buscarEstoque(
            Long idUnidade,
            Long idProduto
    ) {

        return estoqueRepository
                .findByUnidadeIdAndProdutoId(
                        idUnidade,
                        idProduto
                )
                .orElseThrow(() ->
                        new RuntimeException("Estoque não encontrado"));
    }
}