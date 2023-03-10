drop table if exists "user" cascade;

CREATE TABLE "user" (
    id          integer primary key identity,
    email       varchar(20) not null,
    password    varchar(20)
);