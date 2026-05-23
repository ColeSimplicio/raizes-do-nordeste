package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.application.dto.request.EstoqueRequest;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "estoque")
@Entity(name = "Estoque")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Estoque(EstoqueRequest dados,Unidade unidade, Produto produto) {
        this.quantidade = dados.quantidade();
        this.unidade = unidade;
        this.produto = produto;
    }
}