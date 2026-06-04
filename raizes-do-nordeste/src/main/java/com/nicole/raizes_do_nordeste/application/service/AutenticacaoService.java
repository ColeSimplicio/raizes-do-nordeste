package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.nicole.raizes_do_nordeste.api.exception.UnauthorizedException;
import com.nicole.raizes_do_nordeste.application.dto.request.LoginRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.LoginResponse;
import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import com.nicole.raizes_do_nordeste.infrastructure.security.JwtService;
import com.nicole.raizes_do_nordeste.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new UnauthorizedException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(token);
    }
}
