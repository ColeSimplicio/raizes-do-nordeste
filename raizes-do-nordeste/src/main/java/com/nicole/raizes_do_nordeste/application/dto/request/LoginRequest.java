package com.nicole.raizes_do_nordeste.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String email, @NotNull String senha) {
}
