CREATE SCHEMA IF NOT EXISTS "sec";

CREATE TABLE IF NOT EXISTS "cart" (
    "user_id" BIGSERIAL NOT NULL REFERENCES "app_user" (id),
    "product_id" BIGSERIAL NOT NULL REFERENCES "product" (id),
    "amount" INTEGER NOT NULL,
    UNIQUE (user_id, product_id)
);