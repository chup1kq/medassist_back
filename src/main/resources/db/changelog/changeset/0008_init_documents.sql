CREATE TABLE IF NOT EXISTS document_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS documents
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    description      TEXT,
    document_type_id INTEGER      NOT NULL,
    file_url         VARCHAR(512),

    CONSTRAINT fk_documents_type
        FOREIGN KEY (document_type_id)
            REFERENCES document_types(id)
            ON DELETE RESTRICT
);