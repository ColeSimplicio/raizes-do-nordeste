package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.enums.Regiao;
import com.nicole.raizes_do_nordeste.domain.enums.Tipo;
import com.nicole.raizes_do_nordeste.domain.model.Unidade;

import java.math.BigDecimal;

public record UnidadeResponse(
        Long id,
        String nome,
        Regiao regiao,
        Tipo tipo,
        BigDecimal saldo,
        CardapioResponse cardapio
) {
    public UnidadeResponse(Unidade unidade) {
        this(
                unidade.getId(),
                unidade.getNome(),
                unidade.getRegiao(),
                unidade.getTipo(),
                unidade.getSaldo(),
                new CardapioResponse(unidade.getCardapio())
        );
    }
}