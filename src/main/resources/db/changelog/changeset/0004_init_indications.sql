CREATE TABLE IF NOT EXISTS medical_condition
(
    id   SERIAL PRIMARY KEY,
    text TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS service_indications
(
    service_id   INTEGER NOT NULL,
    condition_id INTEGER NOT NULL,

    CONSTRAINT fk_service_indication_service
        FOREIGN KEY (service_id)
            REFERENCES services (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_service_indication_condition
        FOREIGN KEY (condition_id)
            REFERENCES medical_condition (id)
            ON DELETE CASCADE,

    CONSTRAINT pk_service_indications
        PRIMARY KEY (service_id, condition_id)
);

CREATE TABLE IF NOT EXISTS service_contraindications
(
    service_id   INTEGER NOT NULL,
    condition_id INTEGER NOT NULL,

    CONSTRAINT fk_service_contra_service
        FOREIGN KEY (service_id)
            REFERENCES services (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_service_contra_condition
        FOREIGN KEY (condition_id)
            REFERENCES medical_condition (id)
            ON DELETE CASCADE,

    CONSTRAINT pk_service_contraindications
        PRIMARY KEY (service_id, condition_id)
);