create table pedidos (
    id uuid not null,
    instante timestamp not null,
    status int4 not null,
    primary key (id)
);