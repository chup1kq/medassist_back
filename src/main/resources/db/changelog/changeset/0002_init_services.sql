CREATE TABLE IF NOT EXISTS services
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    details     TEXT,
    preparation TEXT,
    url         VARCHAR(120) UNIQUE,
    photo_url   VARCHAR(500)
);