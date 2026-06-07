INSERT INTO unidade (nome, regiao, tipo, saldo)
VALUES (
           'Raízes do Nordeste - Fortaleza',
           'NORDESTE',
           'COMPLETA',
           1000.00
       );

INSERT INTO produto (nome_produto)
VALUES
    ('Acarajé'),
    ('Baião de Dois'),
    ('Carne de Sol'),
    ('Macaxeira Frita'),
    ('Tapioca de Coco'),
    ('Cuscuz Nordestino'),
    ('Bolo de Rolo'),
    ('Sarapatel'),
    ('Mungunzá'),
    ('Refrigerante Guaraná');

INSERT INTO usuario (
    nome,
    email,
    senha,
    pontos_fidelidade,
    role,
    consentimento_lgpd
)
VALUES (
           'Cliente Teste',
           'cliente@gmail.com',
           '$2a$12$MqZcY8D49owDkPPUF1YgpemqXrhgX8sU0Ydv7Q1xvFxvScL0/RBQS',
           0,
           'CLIENTE',
           true
       );

INSERT INTO cardapio (disponivel, unidade_id)
VALUES (true, 1);

INSERT INTO item_cardapio (preco, disponivel, cardapio_id, produto_id, sazonal)
VALUES
    (12.90, true, 1, 1, false), -- Acarajé
    (18.90, true, 1, 2, false), -- Baião de Dois
    (25.00, true, 1, 3, false), -- Carne de Sol
    (10.00, true, 1, 4, false), -- Macaxeira
    (8.50, true, 1, 5, false),  -- Tapioca
    (9.90, true, 1, 6, false),  -- Cuscuz
    (14.90, true, 1, 7, false), -- Bolo de Rolo
    (22.00, true, 1, 8, false), -- Sarapatel
    (7.50, true, 1, 9, false),  -- Mungunzá
    (6.00, true, 1, 10, false); -- Guaraná

INSERT INTO estoque (quantidade, reservado, unidade_id, produto_id)
VALUES
    (50, 0, 1, 1),
    (50, 0, 1, 2),
    (50, 0, 1, 3),
    (50, 0, 1, 4),
    (50, 0, 1, 5),
    (50, 0, 1, 6),
    (50, 0, 1, 7),
    (50, 0, 1, 8),
    (50, 0, 1, 9),
    (100, 0, 1, 10);
