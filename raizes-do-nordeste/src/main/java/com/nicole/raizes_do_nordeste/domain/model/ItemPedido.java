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
    @Setter
    private Integer quantidade;
    @Setter
    private BigDecimal precoUnitario;

    public ItemPedido(ItemPedidoRequest dados, Produto produto, Pedido pedido) {
        this.quantidade = dados.quantidade();
        this.produto = produto;
        this.pedido = pedido;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    @Setter
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @Setter
    private Pedido pedido;


}
