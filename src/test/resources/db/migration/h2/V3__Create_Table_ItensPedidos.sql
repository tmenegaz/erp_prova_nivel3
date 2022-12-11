create table itens_pedidos (
    desconto float8 not null,
    preco float8 not null,
    quantidade int4 not null,
    pedido_id uuid not null,
    catalogo_id uuid not null,
    foreign key (pedido_id) references pedidos,
    foreign key (catalogo_id) references catalogos,
    primary key (pedido_id, catalogo_id)
);