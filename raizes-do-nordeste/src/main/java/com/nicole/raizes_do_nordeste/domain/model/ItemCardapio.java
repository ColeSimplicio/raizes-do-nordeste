package com.nicole.raizes_do_nordeste.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "ItemCardapio")
@Table(name = "item_cardapio")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCardapio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal preco;

    private boolean disponivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardapio_id")
    private Cardapio cardapio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public void itemDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }
}
