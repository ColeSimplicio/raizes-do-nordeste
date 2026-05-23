package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import com.nicole.raizes_do_nordeste.domain.enums.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Integer pontosFidelidade,
        Role role
) {
    public UsuarioResponse(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPontosFidelidade(),
                usuario.getRole()
        );
    }
}