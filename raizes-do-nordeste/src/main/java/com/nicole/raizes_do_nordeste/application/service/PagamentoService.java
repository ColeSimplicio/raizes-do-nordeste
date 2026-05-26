package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.PagamentoMockRequest;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.*;
import com.nicole.raizes_do_nordeste.repository.EstoqueRepository;
import com.nicole.raizes_do_nordeste.repository.PagamentoRepository;
import com.nicole.raizes_do_nordeste.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Transactional
    public Pagamento processarPagamento(
            Long pedidoId,
            PagamentoMockRequest dados
    ) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        Pagamento pagamento = pedido.getPagamento();

        if (pagamento == null) {
            throw new RuntimeException(
                    "Pagamento não encontrado"
            );
        }

        if (pagamento.getStatusPagamento()
                != StatusPagamento.PENDENTE) {

            throw new RuntimeException(
                    "Pagamento já processado"
            );
        }

        if (dados.aprovado()) {

            pagamento.aprovarPagamento();

            pedido.atualizarStatus(
                    StatusPedido.ENTREGUE
            );

            for (ItemPedido item : pedido.getItens()) {

                Estoque estoque =
                        estoqueRepository
                                .findByUnidadeIdAndProdutoId(
                                        pedido.getUnidade().getId(),
                                        item.getProduto().getId()
                                )
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Estoque não encontrado"
                                        ));

                estoque.setQuantidade(
                        estoque.getQuantidade()
                                - item.getQuantidade()
                );
            }

            pedido.getUnidade().setSaldo(
                    pedido.getUnidade()
                            .getSaldo()
                            .add(pedido.getValorTotal())
            );

            pedido.getUsuario().setPontosFidelidade(
                    pedido.getUsuario()
                            .getPontosFidelidade()
                            + pedido.getValorTotal().intValue()
            );

        } else {

            pagamento.recusarPagamento();

            pedido.cancelarPedido();
        }

        pagamentoRepository.save(pagamento);

        return pagamento;
    }
}
