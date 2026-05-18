package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.domain.enums.Regiao;
import com.nicole.raizes_do_nordeste.domain.enums.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "unidade")
@Entity(name = "Unidade")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Unidade {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Regiao regiao;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private BigDecimal saldo;

    @OneToMany(mappedBy = "unidade")
    private List<Estoque> estoques = new ArrayList<>();

    @OneToMany(mappedBy = "unidade")
    private List<Cardapio> cardapio = new ArrayList<>();

    @OneToMany(mappedBy = "unidade")
    private List<Pedido> pedidos = new ArrayList<>();
}
