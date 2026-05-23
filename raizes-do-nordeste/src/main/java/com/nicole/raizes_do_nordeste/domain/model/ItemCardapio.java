package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioPrecoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioRequest;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "ItemCardapio")
@Table(
        name = "item_cardapio",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cardapio_id", "produto_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCardapio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private BigDecimal preco;

    @Getter
    @Setter
    private boolean disponivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardapio_id")
    @Getter
    @Setter
    private Cardapio cardapio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    @Getter
    @Setter
    private Produto produto;

    public ItemCardapio(ItemCardapioRequest dados, Cardapio cardapio, Produto produto) {
        this.preco = dados.preco();
        this.disponivel = dados.disponivel() != null ? dados.disponivel() : true;
        this.cardapio = cardapio;
        this.produto = produto;
    }

//    public void ativar() {
//        if(dataInicio <= LocalDate.now() && dataFim >= LocalDate.now()){
//            this.disponivel = true;
//        }
//
//    }

    public void desativar() {

        this.disponivel = false;
    }

    public void editarPreco(ItemCardapioPrecoRequest dados){
        if(dados.preco() != null){
            this.preco = dados.preco();
        }
    }
}
