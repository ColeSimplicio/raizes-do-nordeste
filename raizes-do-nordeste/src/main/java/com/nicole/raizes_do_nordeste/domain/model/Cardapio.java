package com.nicole.raizes_do_nordeste.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "cardapio")
@Entity(name = "Cardapio")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean disponivel;

    @OneToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @OneToMany(mappedBy = "cardapio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItemCardapio> itens = new ArrayList<>();

    public Cardapio(Unidade unidade) {
        this.disponivel = true;
        this.unidade = unidade;
    }

    public void adicionarItem(ItemCardapio item){
        this.itens.add(item);
    }

    public void removerItem(ItemCardapio item){
        this.itens.remove(item);
    }
}
