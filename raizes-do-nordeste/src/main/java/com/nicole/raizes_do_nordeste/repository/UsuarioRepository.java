package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}
