CREATE TABLE usuario (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         pontos_fidelidade INTEGER NOT NULL,
                         role VARCHAR(50) NOT NULL,
                         consentimento_lgpd BOOLEAN NOT NULL
);

CREATE TABLE unidade (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         regiao VARCHAR(50) NOT NULL,
                         tipo VARCHAR(50) NOT NULL,
                         saldo NUMERIC(10,2)
);

CREATE TABLE produto (
                         id BIGSERIAL PRIMARY KEY,
                         nome_produto VARCHAR(255) NOT NULL
);

CREATE TABLE cardapio (
                          id BIGSERIAL PRIMARY KEY,
                          disponivel BOOLEAN NOT NULL,
                          data_inicio DATE,
                          data_fim DATE,

                          unidade_id BIGINT,

                          CONSTRAINT fk_cardapio_unidade
                              FOREIGN KEY (unidade_id)
                                  REFERENCES unidade(id)
);

CREATE TABLE item_cardapio (
                               id BIGSERIAL PRIMARY KEY,

                               preco NUMERIC(10,2) NOT NULL,
                               disponivel BOOLEAN NOT NULL,

                               cardapio_id BIGINT NOT NULL,
                               produto_id BIGINT NOT NULL,

                               CONSTRAINT fk_item_cardapio_cardapio
                                   FOREIGN KEY (cardapio_id)
                                       REFERENCES cardapio(id),

                               CONSTRAINT fk_item_cardapio_produto
                                   FOREIGN KEY (produto_id)
                                       REFERENCES produto(id)
);

CREATE TABLE pedido (
                        id BIGSERIAL PRIMARY KEY,

                        status_pedido VARCHAR(50) NOT NULL,
                        canal_pedido VARCHAR(50) NOT NULL,

                        hora_pedido TIMESTAMP,
                        valor_pedido NUMERIC(10,2),
                        desconto_pedido NUMERIC(10,2),
                        valor_total NUMERIC(10,2),

                        usuario_id BIGINT,
                        unidade_id BIGINT,

                        CONSTRAINT fk_pedido_usuario
                            FOREIGN KEY (usuario_id)
                                REFERENCES usuario(id),

                        CONSTRAINT fk_pedido_unidade
                            FOREIGN KEY (unidade_id)
                                REFERENCES unidade(id)
);

CREATE TABLE item_pedido (
                             id BIGSERIAL PRIMARY KEY,

                             quantidade INTEGER NOT NULL,
                             preco_unitario NUMERIC(10,2) NOT NULL,

                             produto_id BIGINT NOT NULL,
                             pedido_id BIGINT NOT NULL,

                             CONSTRAINT fk_item_pedido_produto
                                 FOREIGN KEY (produto_id)
                                     REFERENCES produto(id),

                             CONSTRAINT fk_item_pedido_pedido
                                 FOREIGN KEY (pedido_id)
                                     REFERENCES pedido(id)
);

CREATE TABLE pagamento (
                           id BIGSERIAL PRIMARY KEY,

                           status_pagamento VARCHAR(50) NOT NULL,
                           metodo_pagamento VARCHAR(50) NOT NULL,
                           data_hora TIMESTAMP,

                           pedido_id BIGINT UNIQUE,

                           CONSTRAINT fk_pagamento_pedido
                               FOREIGN KEY (pedido_id)
                                   REFERENCES pedido(id)
);

CREATE TABLE estoque (
                         id BIGSERIAL PRIMARY KEY,

                         quantidade INTEGER NOT NULL,

                         unidade_id BIGINT NOT NULL,
                         produto_id BIGINT NOT NULL,

                         CONSTRAINT fk_estoque_unidade
                             FOREIGN KEY (unidade_id)
                                 REFERENCES unidade(id),

                         CONSTRAINT fk_estoque_produto
                             FOREIGN KEY (produto_id)
                                 REFERENCES produto(id)
);