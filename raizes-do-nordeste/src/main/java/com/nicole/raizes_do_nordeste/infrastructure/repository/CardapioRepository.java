package com.nicole.raizes_do_nordeste.infrastructure.repository;

import com.nicole.raizes_do_nordeste.domain.model.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}
