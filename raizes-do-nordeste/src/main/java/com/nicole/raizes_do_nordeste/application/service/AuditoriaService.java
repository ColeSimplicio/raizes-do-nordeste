package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.domain.model.Auditoria;
import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import com.nicole.raizes_do_nordeste.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public void registrar( String acao, String detalhes, Usuario usuario)
    {

        Auditoria auditoria = new Auditoria(acao, detalhes, usuario);

        auditoriaRepository.save(auditoria);
    }
}
