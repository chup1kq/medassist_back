CREATE TABLE IF NOT EXISTS medical_facilities
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE specialist_facilities
(
    id            SERIAL PRIMARY KEY,
    specialist_id INTEGER NOT NULL,
    facility_id   INTEGER NOT NULL,

    CONSTRAINT fk_sf_specialist
        FOREIGN KEY (specialist_id)
            REFERENCES specialists (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_sf_facility
        FOREIGN KEY (facility_id)
            REFERENCES medical_facilities (id)
            ON DELETE CASCADE,

    CONSTRAINT unique_specialist_facility
        UNIQUE (specialist_id, facility_id)
);