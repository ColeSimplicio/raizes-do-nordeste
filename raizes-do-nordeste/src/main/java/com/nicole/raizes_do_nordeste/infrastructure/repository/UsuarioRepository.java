package com.nicole.raizes_do_nordeste.infrastructure.repository;

import com.nicole.raizes_do_nordeste.domain.model.Usuario;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(@NotNull String email);
}
