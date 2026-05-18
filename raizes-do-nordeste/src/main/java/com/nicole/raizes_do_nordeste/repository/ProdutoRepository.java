package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
