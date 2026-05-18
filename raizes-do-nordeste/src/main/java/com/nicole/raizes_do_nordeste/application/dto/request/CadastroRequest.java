package com.nicole.raizes_do_nordeste.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroRequest
        (@NotBlank String nome, @NotBlank @Email String email, @NotBlank @Size(min = 6) String senha,
         @NotNull Boolean consentimento) {
}
