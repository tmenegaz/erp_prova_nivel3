create table pedidos (
    id uuid not null,
    instante timestamp not null,
    status int4,
    primary key (id)
);