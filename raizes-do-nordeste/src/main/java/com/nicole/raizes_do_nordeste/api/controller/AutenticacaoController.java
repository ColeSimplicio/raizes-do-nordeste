package com.nicole.raizes_do_nordeste.api.controller;


import com.nicole.raizes_do_nordeste.application.dto.request.CadastroRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.LoginRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.LoginResponse;
import com.nicole.raizes_do_nordeste.application.dto.response.UsuarioResponse;
import com.nicole.raizes_do_nordeste.application.service.AutenticacaoService;
import com.nicole.raizes_do_nordeste.application.service.UsuarioService;
import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    @RequestMapping("/register")
    public ResponseEntity cadastro(
            @RequestBody @Valid CadastroRequest dados,
            UriComponentsBuilder uriBuilder
    ) {

        Usuario usuario = usuarioService.criarUsuario(dados);

        var uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UsuarioResponse(usuario));
    }

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return autenticacaoService.login(request);
    }
}





