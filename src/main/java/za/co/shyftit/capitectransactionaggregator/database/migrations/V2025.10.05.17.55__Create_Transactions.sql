CREATE TABLE transactions (
    id BIGSERIAL NOT NULL,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ,
    type_id BIGINT NOT NULL REFERENCES transaction_types(id),
    date TIMESTAMP NOT NULL,
    external_id VARCHAR(200),
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    amount NUMERIC NOT NULL,
    description VARCHAR(200)
);