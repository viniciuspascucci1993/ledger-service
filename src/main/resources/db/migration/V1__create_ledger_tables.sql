CREATE TABLE lancamentos (
    id            UUID         PRIMARY KEY,
    criado_em     TIMESTAMPTZ  NOT NULL
);

CREATE TABLE partidas (
    id                  UUID             PRIMARY KEY,
    lancamento_id       UUID             NOT NULL REFERENCES lancamentos (id),
    conta_id            UUID             NOT NULL,
    tipo                VARCHAR(7)       NOT NULL CHECK ( tipo IN ( 'CREDITO' , 'DEBITO' ) ),
    valor_em_centavos   BIGINT           NOT NULL CHECK ( valor_em_centavos > 0 )
);

CREATE INDEX idx_partidas_conta_id                ON partidas   (conta_id);
CREATE INDEX idx_partidas_lancamentos_id          ON partidas   (lancamento_id);