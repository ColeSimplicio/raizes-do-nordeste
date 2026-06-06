package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.response.AuditoriaResponse;
import com.nicole.raizes_do_nordeste.domain.model.Auditoria;
import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import com.nicole.raizes_do_nordeste.infrastructure.repository.AuditoriaRepository;
import com.nicole.raizes_do_nordeste.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrar(String acao, String detalhes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            username = auth.getPrincipal().toString();
        }

        Usuario usuario = null;

        if (username != null) {
            usuario = usuarioRepository.findByEmail(username)
                    .orElse(null);

        }

        Auditoria auditoria = new Auditoria(acao, detalhes, usuario);

        auditoriaRepository.save(auditoria);
    }

    public Page<AuditoriaResponse> listarAcoes(Pageable pageable) {
        return auditoriaRepository.findAll(pageable)
                .map(AuditoriaResponse::new);
    }
}