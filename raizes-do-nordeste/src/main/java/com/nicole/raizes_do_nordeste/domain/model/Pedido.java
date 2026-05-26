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
    @Setter
    private StatusPedido statusPedido;

    @Enumerated(EnumType.STRING)
    @Setter
    private CanalPedido canalPedido;
    @Setter
    private LocalDateTime horaPedido;
    @Setter
    private BigDecimal valorPedido;
    @Setter
    private BigDecimal descontoPedido;
    @Setter
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @Setter
    private Usuario usuario;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    @Setter
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    @Setter
    private Unidade unidade;

    public Pedido(
            Usuario usuario,
            Unidade unidade,
            CanalPedido canalPedido
    ) {
        this.usuario = usuario;
        this.unidade = unidade;
        this.canalPedido = canalPedido;
        this.horaPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.COZINHA;
        this.descontoPedido = BigDecimal.ZERO;
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
