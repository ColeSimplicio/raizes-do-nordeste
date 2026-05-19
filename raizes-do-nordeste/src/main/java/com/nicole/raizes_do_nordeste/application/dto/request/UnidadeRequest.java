package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.enums.Regiao;
import com.nicole.raizes_do_nordeste.domain.enums.Tipo;

public record UnidadeRequest(String nome, Regiao regiao,Tipo tipo) {
}
