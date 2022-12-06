CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table catalogos (
    id uuid not null,
    condicao int4,
    nome varchar(255),
    preco float8,
    tipo int4,
    primary key (id)
);