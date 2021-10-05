CREATE SCHEMA IF NOT EXISTS "sec";
DROP TABLE IF EXISTS "order_item";
CREATE TABLE "order_item" (
      "id" BIGSERIAL PRIMARY KEY,
      "product_id" BIGINT NOT NULL,
      "price" NUMERIC NOT NULL,
      "amount" INTEGER NOT NULL,
      "order_id" BIGSERIAL NOT NULL REFERENCES "order" (id)
)