package com.nicole.raizes_do_nordeste.infrastructure.repository;

import com.nicole.raizes_do_nordeste.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
