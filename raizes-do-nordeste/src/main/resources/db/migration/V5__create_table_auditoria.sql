CREATE TABLE auditoria (
                           id BIGSERIAL PRIMARY KEY,
                           usuario_id BIGINT,
                           acao VARCHAR(100) NOT NULL,
                           detalhes TEXT,
                           data_hora TIMESTAMP NOT NULL DEFAULT NOW()
);