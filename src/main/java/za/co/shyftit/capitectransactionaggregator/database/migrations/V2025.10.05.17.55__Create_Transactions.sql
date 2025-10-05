CREATE TABLE transactions (
    id BIGSERIAL NOT NULL,
    account_id BIGSERIAL NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ,
    type_id BIGSERIAL NOT NULL REFERENCES transaction_types(id),
    date TIMESTAMP NOT NULL,
    external_id VARCHAR(200),
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP NOT NULL,
    amount NUMERIC NOT NULL,
    description VARCHAR(200)
);