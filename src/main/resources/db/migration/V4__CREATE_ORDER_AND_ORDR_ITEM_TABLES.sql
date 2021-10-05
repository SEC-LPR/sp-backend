CREATE SCHEMA IF NOT EXISTS "sec";
DROP TABLE IF EXISTS "order";
CREATE TABLE "order" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "user_id" BIGINT NOT NULL,
    "total_price" NUMERIC NOT NULL,
    "created_time" TIMESTAMP WITH TIME ZONE NOT NULL
    );
