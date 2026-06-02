package com.nicole.raizes_do_nordeste.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Auditoria")
@Table(name = "auditoria")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Auditoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String acao;
    @Setter
    private String detalhes;
    @Setter
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @Setter
    private Usuario usuario;


    public Auditoria(String acao, String detalhes, Usuario usuario) {
        this.acao = acao;
        this.detalhes = detalhes;
        this.dataHora = LocalDateTime.now();
        this.usuario = usuario;
    }
}
