package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
