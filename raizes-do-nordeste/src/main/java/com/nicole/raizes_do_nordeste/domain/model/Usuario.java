package com.nicole.raizes_do_nordeste.domain.model;
import com.nicole.raizes_do_nordeste.api.exception.RegraNegocioException;
import com.nicole.raizes_do_nordeste.application.dto.request.CadastroRequest;
import com.nicole.raizes_do_nordeste.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "usuario")
@Entity(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique = true)
    private String email;
    @Setter
    private String senha;
    private Integer pontosFidelidade;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "consentimento_lgpd")
    private Boolean consentimentoLGPD;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos = new ArrayList<>();

    public Usuario(CadastroRequest dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = dados.senha();
        this.pontosFidelidade = 0;
        this.role = Role.CLIENTE;
        this.consentimentoLGPD = dados.consentimento();
    }

    public void acumularPontos(Integer pontos){
        if(!Boolean.TRUE.equals(this.consentimentoLGPD)){
            return;
        }
        this.pontosFidelidade += pontos;
    }

    public void resgatarPontos(Integer pontos){

        if(pontos > this.pontosFidelidade){
            throw new RegraNegocioException(
                    "Pontos insuficientes"
            );
        }

        this.pontosFidelidade -= pontos;
    }

}
