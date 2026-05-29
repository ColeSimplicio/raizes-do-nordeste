package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.nicole.raizes_do_nordeste.api.exception.RegraNegocioException;
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
                        new RecursoNaoEncontradoException("Pedido não encontrado"));

        Pagamento pagamento = pedido.getPagamento();

        if (pagamento == null) {
            throw new RecursoNaoEncontradoException(
                    "Pagamento não encontrado"
            );
        }

        if (pagamento.getStatusPagamento()
                != StatusPagamento.PENDENTE) {

            throw new RegraNegocioException(
                    "Pagamento já processado"
            );
        }

        if (dados.aprovado()) {

            pagamento.aprovarPagamento();

            pedido.atualizarStatus(
                    StatusPedido.PRONTO
            );

            for (ItemPedido item : pedido.getItens()) {

                Estoque estoque =
                        estoqueRepository
                                .findByUnidadeIdAndProdutoId(
                                        pedido.getUnidade().getId(),
                                        item.getProduto().getId()
                                )
                                .orElseThrow(() ->
                                        new RecursoNaoEncontradoException(
                                                "Estoque não encontrado"
                                        ));

                estoque.setQuantidade(
                        estoque.getQuantidade()
                                - item.getQuantidade()
                );

                estoque.setReservado(
                        estoque.getReservado()
                                - item.getQuantidade()
                );
            }

            pedido.getUnidade().setSaldo(
                    pedido.getUnidade()
                            .getSaldo()
                            .add(pedido.getValorTotal())
            );

            pedido.getUsuario().acumularPontos(
                    pedido.getValorTotal().intValue()
            );

        } else {

            pagamento.recusarPagamento();

            pedido.cancelarPedido();

            for (ItemPedido item : pedido.getItens()) {

                Estoque estoque =
                        estoqueRepository
                                .findByUnidadeIdAndProdutoId(
                                        pedido.getUnidade().getId(),
                                        item.getProduto().getId()
                                )
                                .orElseThrow(() ->
                                        new RecursoNaoEncontradoException(
                                                "Estoque não encontrado"
                                        ));

                estoque.setReservado(
                        estoque.getReservado()
                                - item.getQuantidade()
                );
            }
        }

        pagamentoRepository.save(pagamento);

        return pagamento;
    }
}
