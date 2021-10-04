CREATE SCHEMA if not exists "sec";

DROP TABLE IF EXISTS "app_user";
CREATE TABLE "app_user" (
    "id"          BIGSERIAL PRIMARY KEY,
    "username"    VARCHAR(255) UNIQUE NOT NULL,
    "password"    VARCHAR(255)        NOT NULL,
    "credit_card" VARCHAR(255)
);


DROP TABLE IF EXISTS "prodcut";
CREATE TABLE "product" (
    "id"          BIGSERIAL PRIMARY KEY,
    "name"        VARCHAR(255) UNIQUE NOT NULL,
    "description" VARCHAR(2048),
    "price"       NUMERIC             NOT NULL,
    "amount"      INTEGER             NOT NULL
);