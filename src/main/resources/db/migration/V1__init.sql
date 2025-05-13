CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       full_name VARCHAR(255) NOT NULL
);

CREATE TABLE subscriptions (
                               id SERIAL PRIMARY KEY,
                               service_name VARCHAR(255) NOT NULL,
                               user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);