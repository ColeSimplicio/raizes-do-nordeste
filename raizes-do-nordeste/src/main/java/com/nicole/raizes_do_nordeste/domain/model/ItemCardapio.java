package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioEdicaoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemCardapioRequest;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Getter
    @Setter
    private boolean sazonal;
    @Getter
    @Setter
    private LocalDate dataInicio;
    @Getter
    @Setter
    private LocalDate dataFim;

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

    public void editarItem(ItemCardapioEdicaoRequest dados){

        if(dados.preco() != null){
            this.preco = dados.preco();
        }

        if(dados.disponivel() != null){
            this.disponivel = dados.disponivel();
        }

        if(dados.sazonal() != null){

            this.sazonal = dados.sazonal();

            if(Boolean.FALSE.equals(dados.sazonal())){
                this.dataInicio = null;
                this.dataFim = null;
            }
        }

        if(dados.dataInicio() != null){
            this.dataInicio = dados.dataInicio();
        }

        if(dados.dataFim() != null){
            this.dataFim = dados.dataFim();
        }
    }


}
