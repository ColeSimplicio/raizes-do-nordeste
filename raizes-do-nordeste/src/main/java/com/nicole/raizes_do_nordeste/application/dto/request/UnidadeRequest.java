package com.nicole.raizes_do_nordeste.application.dto.request;

import com.nicole.raizes_do_nordeste.domain.enums.Regiao;
import com.nicole.raizes_do_nordeste.domain.enums.Tipo;
import jakarta.validation.constraints.NotBlank;

public record UnidadeRequest(@NotBlank String nome, @NotBlank Regiao regiao, @NotBlank Tipo tipo) {
}
