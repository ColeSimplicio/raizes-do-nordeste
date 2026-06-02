package com.nicole.raizes_do_nordeste.infrastructure.repository;

import com.nicole.raizes_do_nordeste.domain.model.Estoque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);

    Page<Estoque> findByUnidadeId(Long unidadeId, Pageable pageable);
}
