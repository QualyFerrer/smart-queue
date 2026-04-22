-- MIGRATION V1: Criação do schema completo do SmartQueue

-- ORGANIZATIONS
-- Representa uma clínica, cartório, ou qualquer estabelecimento
-- que usa o sistema de filas

CREATE TABLE organizations
(
    id         UUID PRIMARY KEY     DEFAULT gen_random_uuid() name                VARCHAR(100)    NOT NULL,
    cnpj       VARCHAR(18) NOT NULL UNIQUE,
    active     BOOLEAN     NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- SERVICE POINTS
-- Os guichês ou consultórios dentro de uma organização
-- Uma organização pode ter vários service points

CREATE TABLE service_points
(
    id              UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    organization_id UUID         NOT NULL,
    name            VARCHAR(100) NOT NULL,
    active          BOOLEAN      NOT NULL DEFAULT TRUE,

    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    -- FOREIGN KEY: garante que não existe service_point sem organization
    -- ON DELETE CASCADE: se a organization for deletada, os service_points também são
    CONSTRAINT fk_service_points_organization
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id)
            ON DELETE CASCADE
);

CREATE TYPE user_role AS ENUM ('ADMIN', 'ATTENDANT');


CREATE TABLE users
(
    id              UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    organization_id UUID         NOT NULL,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    hash_password   VARCHAR(255) NOT NULL,

    role            user_role    NOT NULL DEFAULT 'ATTENDANT',
    active          BOOLEAN      NOT NULL DEFAULT TRUE,

    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_users_organization
        FOREIGN KEY (organization_id)
            REFERENCES organizations (id)
            ON DELETE CASCADE
);

-- ÍNDICES
-- Melhoram a performance de queries frequentes

-- buscar tickets de uma fila
CREATE INDEX idx_tickets_queue ON tickets(queue_id);

-- buscar tickets pelo status (ex: todos os tickets com status WAITING)
CREATE INDEX idx_tickets_status ON tickets(status);

-- buscar filas de um service point, ou seja, de um guichê
CREATE INDEX idx_queue_service_point ON queues(service_point_id);

--bsucar usuários por email
CREATE INDEX idx_users_email ON users(email);

)