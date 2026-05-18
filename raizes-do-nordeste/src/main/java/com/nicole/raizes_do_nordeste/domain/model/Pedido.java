package com.nicole.raizes_do_nordeste.domain.model;


import com.nicole.raizes_do_nordeste.application.dto.request.CriarPedidoRequest;
import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pedido")
@Entity(name = "Pedido")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Enumerated(EnumType.STRING)
    private CanalPedido canalPedido;

    private LocalDateTime horaPedido;
    private BigDecimal valorPedido;
    private BigDecimal descontoPedido;
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    public Pedido(CriarPedidoRequest dados) {
        statusPedido = StatusPedido.COZINHA;
        canalPedido = dados.canalPedido();
        horaPedido = LocalDateTime.now();
        valorPedido = dados.valorPedido();
        descontoPedido = dados.desconto();
        valorTotal = dados.valorTotal();
        itens = dados.itens();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
    }

    public void atualizarStatus(StatusPedido status){
        statusPedido = status;
    }

    public void cancelarPedido(){
        atualizarStatus(StatusPedido.CANCELADO);

    }

    public BigDecimal calcularTotal(){
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}
