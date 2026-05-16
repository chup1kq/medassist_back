CREATE TABLE IF NOT EXISTS medical_facility_schedules
(
    id                    SERIAL PRIMARY KEY,
    medical_facility_id   INTEGER NOT NULL,
    day_of_week           INTEGER NOT NULL,
    start_time            TIME,
    end_time              TIME,
    is_closed             BOOLEAN DEFAULT FALSE NOT NULL,
    is_24_hours           BOOLEAN DEFAULT FALSE NOT NULL,

    CONSTRAINT fk_medical_facility
        FOREIGN KEY (medical_facility_id)
            REFERENCES medical_facilities (id)
            ON DELETE CASCADE,

    CONSTRAINT unique_facility_day
        UNIQUE (medical_facility_id, day_of_week)
);

CREATE INDEX idx_medical_facility_id ON medical_facility_schedules (medical_facility_id);


