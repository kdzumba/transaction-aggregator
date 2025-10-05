CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    account_number VARCHAR(100),
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP
);