create table catalogos (
    id uuid not null,
    condicao int4,
    nome varchar(255) not null,
    preco float8 not null,
    tipo int4 not null,
    primary key (id)
);