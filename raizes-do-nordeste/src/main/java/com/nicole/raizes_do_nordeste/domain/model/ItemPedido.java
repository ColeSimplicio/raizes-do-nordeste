package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.application.dto.request.ItemPedidoRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "item_pedido")
@Entity(name = "ItemPedido")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class ItemPedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido(ItemPedidoRequest dados) {
        this.quantidade = dados.quantidade();
        this.precoUnitario = dados.precoUnitario();
        this.produto = dados.produto();
        this.pedido = dados.pedido();
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @Setter
    private Pedido pedido;


}
