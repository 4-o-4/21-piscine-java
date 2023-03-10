drop table if exists product cascade;

CREATE TABLE product (
    id          integer primary key identity,
    name        varchar(20),
    price       integer
);
