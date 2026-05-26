package com.nicole.raizes_do_nordeste.domain.model;

import com.nicole.raizes_do_nordeste.domain.enums.MetodoPagamento;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "pagamento")
@Entity(name = "Pagamento")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Pagamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Setter
    private StatusPagamento statusPagamento;
    @Enumerated(EnumType.STRING)
    @Setter
    private MetodoPagamento metodoPagamento;
    @Setter
    private LocalDateTime dataHora;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", unique = true)
    @Setter
    private Pedido pedido;

    public void aprovarPagamento(){
        this.statusPagamento = StatusPagamento.CONFIRMADO;
    }

    public void recusarPagamento(){
        this.statusPagamento = StatusPagamento.NEGADO;
    }
}
