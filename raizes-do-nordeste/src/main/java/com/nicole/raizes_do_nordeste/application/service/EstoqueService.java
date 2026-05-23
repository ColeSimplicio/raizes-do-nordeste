package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.EstoqueRequest;
import com.nicole.raizes_do_nordeste.domain.model.Estoque;
import com.nicole.raizes_do_nordeste.domain.model.Produto;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;
import com.nicole.raizes_do_nordeste.repository.EstoqueRepository;
import com.nicole.raizes_do_nordeste.repository.ProdutoRepository;
import com.nicole.raizes_do_nordeste.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    public void adicionarProdutoNoEstoque(Long idUnidade, Long idProduto, EstoqueRequest dados) {

        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Optional<Estoque> estoqueOpt =
                estoqueRepository.findByUnidadeIdAndProdutoId(idUnidade, idProduto);

        Estoque estoque;

        if (estoqueOpt.isPresent()) {
            estoque = estoqueOpt.get();
            estoque.setQuantidade(estoque.getQuantidade() + dados.quantidade());
        } else {
            estoque = new Estoque(dados, unidade, produto);
        }

        estoqueRepository.save(estoque);
    }

    public void entradaEstoque(Long idUnidade, Long idProduto, Integer quantidade) {

        Estoque estoque = buscarEstoque(idUnidade, idProduto);

        estoque.setQuantidade(
                estoque.getQuantidade() + quantidade
        );

        estoqueRepository.save(estoque);
    }

    public void saidaEstoque(Long idUnidade, Long idProduto, Integer quantidade) {

        Estoque estoque = buscarEstoque(idUnidade, idProduto);

        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        estoque.setQuantidade(
                estoque.getQuantidade() - quantidade
        );

        estoqueRepository.save(estoque);
    }

    public void atualizarQuantidade(Long idUnidade, Long idProduto, Integer novaQuantidade) {

        Estoque estoque = buscarEstoque(idUnidade, idProduto);

        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }

        estoque.setQuantidade(novaQuantidade);

        estoqueRepository.save(estoque);
    }

    public List<Estoque> listarEstoqueDaUnidade(Long idUnidade) {

        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        return estoqueRepository.findByUnidadeId(unidade.getId());
    }

    private Estoque buscarEstoque(Long idUnidade, Long idProduto) {

        return estoqueRepository.findByUnidadeIdAndProdutoId(idUnidade, idProduto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
    }
}
