
ALTER TABLE cardapio
    ADD CONSTRAINT uk_cardapio_unidade
        UNIQUE (unidade_id);

ALTER TABLE estoque
    ADD CONSTRAINT uk_estoque_unidade_produto
        UNIQUE (unidade_id, produto_id);


ALTER TABLE item_cardapio
    ADD CONSTRAINT uk_item_cardapio
        UNIQUE (cardapio_id, produto_id);