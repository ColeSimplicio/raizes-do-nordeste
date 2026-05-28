package com.nicole.raizes_do_nordeste.repository;

import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByCanalPedido(
            CanalPedido canalPedido,
            Pageable pageable
    );
}
