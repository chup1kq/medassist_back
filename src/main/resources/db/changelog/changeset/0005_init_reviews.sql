CREATE TABLE IF NOT EXISTS reviews
(
    id            SERIAL PRIMARY KEY,

    service_id    INTEGER,
    specialist_id INTEGER,

    author_name   VARCHAR(255),
    rating        INTEGER CHECK (rating BETWEEN 1 AND 5),
    text          TEXT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_service
        FOREIGN KEY (service_id)
            REFERENCES services (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_review_specialist
        FOREIGN KEY (specialist_id)
            REFERENCES specialists (id)
            ON DELETE CASCADE,

    CONSTRAINT review_target_check
        CHECK (
            (service_id IS NOT NULL AND specialist_id IS NULL) OR
            (service_id IS NULL AND specialist_id IS NOT NULL)
            )
);