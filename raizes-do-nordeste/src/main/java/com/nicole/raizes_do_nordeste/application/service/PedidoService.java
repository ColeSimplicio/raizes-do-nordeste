package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.application.dto.request.CriarPedidoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemPedidoRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.PedidoResponse;
import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.*;
import com.nicole.raizes_do_nordeste.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Transactional
    public Pedido criarPedido(CriarPedidoRequest dados) {

        Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Unidade unidade = unidadeRepository.findById(dados.unidadeId())
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        Pedido pedido = new Pedido(
                usuario,
                unidade,
                dados.canalPedido()
        );

        for (ItemPedidoRequest itemRequest : dados.itens()) {


            Produto produto = produtoRepository
                    .findById(itemRequest.produtoId())
                    .orElseThrow(() ->
                            new RuntimeException("Produto não encontrado"));


            ItemCardapio itemCardapio =
                    itemCardapioRepository
                            .findByCardapioUnidadeIdAndProdutoId(
                                    unidade.getId(),
                                    produto.getId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Produto não disponível nessa unidade"
                                    ));

            if (!itemCardapio.isDisponivel()) {
                throw new RuntimeException(
                        "Produto indisponível"
                );
            }

            if (itemCardapio.isSazonal()) {

                LocalDate hoje = LocalDate.now();

                if (hoje.isBefore(itemCardapio.getDataInicio())
                        || hoje.isAfter(itemCardapio.getDataFim())) {

                    throw new RuntimeException(
                            "Produto fora do período sazonal"
                    );
                }
            }

            Estoque estoque =
                    estoqueRepository
                            .findByUnidadeIdAndProdutoId(
                                    unidade.getId(),
                                    produto.getId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Produto sem estoque"
                                    ));

            if (estoque.getQuantidade()
                    < itemRequest.quantidade()) {

                throw new RuntimeException(
                        "Estoque insuficiente"
                );
            }

            ItemPedido itemPedido =
                    new ItemPedido(
                            itemRequest,
                            produto,
                            pedido
                    );

            itemPedido.setPrecoUnitario(
                    itemCardapio.getPreco()
            );

            pedido.adicionarItem(itemPedido);

        }

        BigDecimal total = pedido.calcularTotal();

        pedido.setValorPedido(total);
        pedido.setValorTotal(total);
        pedido.setDescontoPedido(BigDecimal.ZERO);

        pedido.setStatusPedido(
                StatusPedido.COZINHA
        );

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);

        pedido.setPagamento(pagamento);

        pagamento.setStatusPagamento(
                StatusPagamento.PENDENTE
        );

        pagamento.setMetodoPagamento(
                dados.metodoPagamento()
        );

        pedidoRepository.save(pedido);

        pagamento.setPedido(pedido);

        pagamentoRepository.save(pagamento);

        return pedido;
    }

    @Transactional
    public void cancelarPedido(Long id){

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido já cancelado");
        }

        pedido.cancelarPedido();

        Pagamento pagamento = pedido.getPagamento();

        if (pagamento != null) {
            pagamento.setStatusPagamento(
                    StatusPagamento.CANCELADO
            );
        }

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


        }

        pedido.getUnidade().setSaldo(
                pedido.getUnidade()
                        .getSaldo()
                        .subtract(pedido.getValorTotal())
        );

        pedido.getUsuario().setPontosFidelidade(
                pedido.getUsuario()
                        .getPontosFidelidade()
                        - pedido.getValorTotal().intValue()
        );

        pedidoRepository.save(pedido);
    }

    public Pedido buscarPedido(Long id){

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));
    }

    public Page<PedidoResponse> listarPedidos(
            CanalPedido canalPedido,
            Pageable pageable
    ) {

        if (canalPedido != null) {

            return pedidoRepository
                    .findByCanalPedido(
                            canalPedido,
                            pageable
                    )
                    .map(PedidoResponse::new);
        }

        return pedidoRepository
                .findAll(pageable)
                .map(PedidoResponse::new);
    }
}