create table itens_pedidos (
    desconto float8,
    preco float8,
    quantidade int4,
    pedido_id uuid not null,
    catalogo_id uuid not null,
    foreign key (pedido_id) references pedidos,
    foreign key (catalogo_id) references catalogos,
    primary key (pedido_id, catalogo_id)
);