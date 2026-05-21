-- SARC - Modelo relacional inicial para PostgreSQL

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(200) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_usuario_email UNIQUE (email),
    CONSTRAINT ck_usuario_perfil CHECK (perfil IN ('PROFESSOR', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS recurso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    numero_sala VARCHAR(20),
    localizacao VARCHAR(100),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_recurso_tipo CHECK (tipo IN ('SALA', 'LABORATORIO', 'COMPUTADOR', 'PROJETOR', 'EQUIPAMENTO'))
);

CREATE TABLE IF NOT EXISTS alocacao (
    id SERIAL PRIMARY KEY,
    professor_id INTEGER NOT NULL,
    disciplina VARCHAR(120) NOT NULL,
    data DATE NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fim TIME NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alocacao_professor
        FOREIGN KEY (professor_id)
        REFERENCES usuario (id),
    CONSTRAINT ck_alocacao_horario
        CHECK (horario_fim > horario_inicio)
);

CREATE TABLE IF NOT EXISTS alocacao_recurso (
    alocacao_id INTEGER NOT NULL,
    recurso_id INTEGER NOT NULL,
    CONSTRAINT pk_alocacao_recurso
        PRIMARY KEY (alocacao_id, recurso_id),
    CONSTRAINT fk_alocacao_recurso_alocacao
        FOREIGN KEY (alocacao_id)
        REFERENCES alocacao (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_alocacao_recurso_recurso
        FOREIGN KEY (recurso_id)
        REFERENCES recurso (id)
);

CREATE INDEX IF NOT EXISTS idx_alocacao_professor_id
    ON alocacao (professor_id);

CREATE INDEX IF NOT EXISTS idx_alocacao_data_horario
    ON alocacao (data, horario_inicio, horario_fim);

CREATE INDEX IF NOT EXISTS idx_alocacao_recurso_recurso_id
    ON alocacao_recurso (recurso_id);
