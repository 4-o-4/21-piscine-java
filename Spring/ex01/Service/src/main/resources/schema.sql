drop table if exists "user" cascade;

CREATE TABLE "user" (
    id          serial primary key,
    email       varchar not null
);