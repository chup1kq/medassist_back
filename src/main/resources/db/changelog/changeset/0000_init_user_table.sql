CREATE TABLE IF NOT EXISTS public.users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255)       NOT NULL
);