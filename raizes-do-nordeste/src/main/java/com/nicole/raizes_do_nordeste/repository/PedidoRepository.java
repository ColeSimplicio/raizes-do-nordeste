package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
