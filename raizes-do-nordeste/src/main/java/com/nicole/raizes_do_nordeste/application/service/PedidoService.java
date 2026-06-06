package com.nicole.raizes_do_nordeste.application.service;

import com.nicole.raizes_do_nordeste.api.exception.ForbiddenException;
import com.nicole.raizes_do_nordeste.api.exception.RecursoNaoEncontradoException;
import com.nicole.raizes_do_nordeste.api.exception.RegraNegocioException;
import com.nicole.raizes_do_nordeste.api.exception.UnauthorizedException;
import com.nicole.raizes_do_nordeste.application.dto.request.CriarPedidoRequest;
import com.nicole.raizes_do_nordeste.application.dto.request.ItemPedidoRequest;
import com.nicole.raizes_do_nordeste.application.dto.response.PedidoResponse;
import com.nicole.raizes_do_nordeste.domain.enums.CanalPedido;
import com.nicole.raizes_do_nordeste.domain.enums.Role;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPagamento;
import com.nicole.raizes_do_nordeste.domain.enums.StatusPedido;
import com.nicole.raizes_do_nordeste.domain.model.*;
import com.nicole.raizes_do_nordeste.infrastructure.repository.*;
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

    @Autowired
    private AuditoriaService auditoriaService;

    @Transactional
    public Pedido criarPedido(CriarPedidoRequest dados, String emailUsuario) {

        Usuario usuario = usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        ));

        Unidade unidade = unidadeRepository.findById(dados.unidadeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Unidade não encontrada"));

        Pedido pedido = new Pedido(
                usuario,
                unidade,
                dados.canalPedido()
        );

        for (ItemPedidoRequest itemRequest : dados.itens()) {


            Produto produto = produtoRepository
                    .findById(itemRequest.produtoId())
                    .orElseThrow(() ->
                            new RecursoNaoEncontradoException("Produto não encontrado"));


            ItemCardapio itemCardapio =
                    itemCardapioRepository
                            .findByCardapioUnidadeIdAndProdutoId(
                                    unidade.getId(),
                                    produto.getId()
                            )
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Produto não disponível nessa unidade"
                                    ));

            if (!itemCardapio.isDisponivel()) {
                throw new RegraNegocioException(
                        "Produto indisponível"
                );
            }

            if (itemCardapio.isSazonal()) {

                LocalDate hoje = LocalDate.now();

                if (hoje.isBefore(itemCardapio.getDataInicio())
                        || hoje.isAfter(itemCardapio.getDataFim())) {

                    throw new RegraNegocioException(
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
                                    new RecursoNaoEncontradoException(
                                            "Produto sem estoque"
                                    ));

            if (estoque.getDisponivel() < itemRequest.quantidade()) {

                throw new RegraNegocioException(
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
            estoque.setReservado(
                    estoque.getReservado()
                            + itemRequest.quantidade()
            );

        }

        BigDecimal valorOriginal = pedido.calcularTotal();

        BigDecimal total = valorOriginal;

        BigDecimal descontoPedido = BigDecimal.ZERO;

        if (dados.pontosUtilizados() != null
                && dados.pontosUtilizados() > 0) {

            Integer pontos = dados.pontosUtilizados();

            if (pontos > usuario.getPontosFidelidade()) {
                throw new RegraNegocioException(
                        "Pontos insuficientes"
                );
            }

            BigDecimal descontoPontos =
                    BigDecimal.valueOf(pontos)
                            .multiply(new BigDecimal("0.10"));

            BigDecimal limiteDesconto =
                    valorOriginal.multiply(new BigDecimal("0.50"));

            if (descontoPontos.compareTo(limiteDesconto) > 0) {

                throw new RegraNegocioException(
                        "O desconto não pode ultrapassar 50% do pedido"
                );
            }

            total = total.subtract(descontoPontos);

            descontoPedido = descontoPontos;

            usuario.resgatarPontos(pontos);
            usuarioRepository.save(usuario);
        }

        pedido.setValorPedido(valorOriginal);

        pedido.setDescontoPedido(descontoPedido);

        pedido.setValorTotal(total);

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

        auditoriaService.registrar(
                "CRIAR_PEDIDO",
                "Pedido criado para usuário "
                        + usuario.getId()
        );
        pagamento.setPedido(pedido);

        pagamentoRepository.save(pagamento);

        return pedido;
    }

    @Transactional
    public void cancelarPedido(Long id,  String emailUsuario){

        Pedido pedido = buscarPedidoDoUsuario(
                id,
                emailUsuario
        );

        if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new RegraNegocioException("Pedido já cancelado");
        }

        Pagamento pagamento = pedido.getPagamento();

        if (pagamento != null
                && pagamento.getStatusPagamento()
                == StatusPagamento.CONFIRMADO) {

            throw new RegraNegocioException(
                    "Pedido pago não pode ser cancelado"
            );
        }

        pedido.cancelarPedido();
        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);

        auditoriaService.registrar(
                "CANCELAR_PEDIDO",
                "Pedido cancelado: " + pedido.getId()
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

            estoque.setReservado(
                    estoque.getReservado()
                            - item.getQuantidade()
            );
        }

        pedidoRepository.save(pedido);
    }

    public Pedido buscarPedido(
            Long id,
            String emailUsuario) {
        return buscarPedidoDoUsuario(
                id,
                emailUsuario
        );
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

    private Pedido buscarPedidoDoUsuario(Long pedidoId, String emailUsuario) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado"
                        ));

        Usuario usuario = usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        ));

        boolean isAdmin =
                usuario.getRole() == Role.ADMIN;

        boolean isDonoDoPedido =
                pedido.getUsuario().getId()
                        .equals(usuario.getId());

        if (!isAdmin && !isDonoDoPedido) {
            throw new ForbiddenException(
                    "Você não pode acessar este pedido"
            );
        }

        return pedido;
    }
}