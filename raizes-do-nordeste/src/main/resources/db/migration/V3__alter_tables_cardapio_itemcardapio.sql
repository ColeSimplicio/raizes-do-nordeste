ALTER TABLE cardapio
DROP COLUMN data_inicio,
DROP COLUMN data_fim;

ALTER TABLE item_cardapio
    ADD COLUMN sazonal BOOLEAN NOT NULL DEFAULT FALSE,

ADD COLUMN data_inicio DATE,

ADD COLUMN data_fim DATE;