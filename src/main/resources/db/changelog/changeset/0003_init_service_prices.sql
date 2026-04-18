CREATE TABLE IF NOT EXISTS service_prices
(
    id         SERIAL PRIMARY KEY,
    service_id INTEGER        NOT NULL,
    name       VARCHAR(255)   NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,

    CONSTRAINT fk_service_price
        FOREIGN KEY (service_id)
            REFERENCES services (id)
            ON DELETE CASCADE
);