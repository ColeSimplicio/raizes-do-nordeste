package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
