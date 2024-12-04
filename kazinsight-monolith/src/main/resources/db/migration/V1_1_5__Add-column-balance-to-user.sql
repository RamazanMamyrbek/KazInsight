ALTER TABLE users
    ADD COLUMN balance FLOAT(53) NOT NULL DEFAULT 0;

ALTER TABLE users
    ADD CONSTRAINT check_balance_nonnegative CHECK (balance >= 0);