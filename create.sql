create table catalogos (id uuid not null, condicao int4, nome varchar(255), preco float8, tipo int4, primary key (id));
create table itens_pedidos (desconto float8, preco float8, quantidade int4, pedido_id uuid not null, catalogo_id uuid not null, primary key (catalogo_id, pedido_id));
create table pedidos (id uuid not null, instante timestamp not null, status int4, primary key (id));
alter table itens_pedidos add constraint FKjg75o3y4r665tq9f72gyk64ut foreign key (pedido_id) references pedidos;
alter table itens_pedidos add constraint FKkyvj1e6sttq2qjakr7pqf6uik foreign key (catalogo_id) references catalogos;