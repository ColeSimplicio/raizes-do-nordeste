package com.nicole.raizes_do_nordeste.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProdutoRequest(@NotBlank String nome) {
}
