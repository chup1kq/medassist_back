CREATE TABLE schedules
(
    id                     SERIAL PRIMARY KEY,
    specialist_facility_id INTEGER NOT NULL,

    day_of_week            INTEGER NOT NULL,
    start_time             TIME    NOT NULL,
    end_time               TIME    NOT NULL,

    CONSTRAINT fk_schedule_sf
        FOREIGN KEY (specialist_facility_id)
            REFERENCES specialist_facilities (id)
            ON DELETE CASCADE
);