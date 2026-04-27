CREATE TABLE IF NOT EXISTS specializations
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS specialists
(
    id               SERIAL PRIMARY KEY,
    full_name        VARCHAR(255) NOT NULL,
    description      TEXT,
    photo_url        VARCHAR(512),
    experience_years INTEGER,
    active           BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS specialist_specializations
(
    specialist_id     INTEGER NOT NULL,
    specialization_id INTEGER NOT NULL,

    PRIMARY KEY (specialist_id, specialization_id),

    CONSTRAINT fk_specialist
        FOREIGN KEY (specialist_id)
            REFERENCES specialists (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_specialization
        FOREIGN KEY (specialization_id)
            REFERENCES specializations (id)
            ON DELETE CASCADE
);