CREATE TABLE wallet.customer_wallet (
    wallet_id VARCHAR(255) PRIMARY KEY,client_id VARCHAR(255) NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    balance BIGINT NOT NULL,
    wallet_type VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL
);

-- Crear tabla "wallet_transaction"

CREATE TABLE wallet.transaction (
    transaction_id VARCHAR(255) PRIMARY KEY,
    client_id VARCHAR(255) NOT NULL,
    wallet_id VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    amount BIGINT NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallet.customer_wallet(wallet_id)
);
