package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.CadastroRequest;
import com.nicole.raizes_do_nordeste.domain.exception.EmailDuplicadoException;
import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import com.nicole.raizes_do_nordeste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(CadastroRequest dados){

        if(usuarioRepository.existsByEmail(dados.email())){
            throw new EmailDuplicadoException();
        }

        Usuario usuario = new Usuario(dados);

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuarioRepository.save(usuario);
    }
}
