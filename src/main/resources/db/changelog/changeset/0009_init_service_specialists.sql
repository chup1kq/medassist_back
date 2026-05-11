CREATE TABLE IF NOT EXISTS service_specialists
(
    service_id    INTEGER NOT NULL,
    specialist_id INTEGER NOT NULL,

    PRIMARY KEY (service_id, specialist_id),

    CONSTRAINT fk_service_specialist_service
        FOREIGN KEY (service_id)
            REFERENCES services (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_service_specialist_specialist
        FOREIGN KEY (specialist_id)
            REFERENCES specialists (id)
            ON DELETE CASCADE
);

