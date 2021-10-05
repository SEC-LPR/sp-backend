ALTER TABLE "app_user" DROP COLUMN "credit_card";

CREATE SCHEMA IF NOT EXISTS "sec";
CREATE TABLE IF NOT EXISTS "credit_card" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "card_name" VARCHAR(255) NOT NULL,
    "card_number" VARCHAR(255) NOT NULL,
    "exp_date" VARCHAR(255) NOT NULL,
    "cvv" VARCHAR(255) NOT NULL,
    "user_id" BIGSERIAL NOT NULL REFERENCES "app_user" (id)
)
