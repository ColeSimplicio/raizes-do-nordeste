package com.nicole.raizes_do_nordeste.application.dto.response;

import com.nicole.raizes_do_nordeste.domain.model.Auditoria;

import java.time.LocalDateTime;

public record AuditoriaResponse(

        Long id,
        String acao,
        String detalhes,
        String usuario,
        LocalDateTime data

) {
    public AuditoriaResponse(Auditoria auditoria) {
        this(
                auditoria.getId(),
                auditoria.getAcao(),
                auditoria.getDetalhes(),
                auditoria.getUsuario() != null ? auditoria.getUsuario().getNome() : null,
                auditoria.getDataHora()
        );
    }
}