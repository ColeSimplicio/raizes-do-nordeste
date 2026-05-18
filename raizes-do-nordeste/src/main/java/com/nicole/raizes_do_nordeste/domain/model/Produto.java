package com.nicole.raizes_do_nordeste.domain.model;


import com.nicole.raizes_do_nordeste.application.dto.request.ProdutoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "produto")
@Entity(name = "Produto")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itemPedido = new ArrayList<>();

    @OneToMany(mappedBy = "produto")
    private List<Estoque> estoques = new ArrayList<>();

    @OneToMany(mappedBy = "produto")
    private List<ItemCardapio> itemCardapio = new ArrayList<>();

    public Produto(ProdutoRequest dados) {
        this.nomeProduto = dados.nome();
    }

    public void atualizarDados(ProdutoRequest dados){
        this.nomeProduto = dados.nome();
    }
}
