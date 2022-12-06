# Para Começar

A Prova nível III foi respondida. Para rodar a aplicação siga as instruções abaixo.

### Dependências

As dependências e configurações estão preparadas para atender a aplicação nos arquivos ``application.yml`` e ``pom.xml``.

O acesso ao banco ``wb_erp_prova`` tem ``username: postgres`` e ``
    password: root``

Utilizei:

* Banco de Dados PostGreSQL 12;
* URL base do catalogo: ``http://localhost:8081/produtosservicos``
* URL base do pedido: ``http://localhost:8081/pedido``
* PORT: 8081
* Schema na ``url = jdbc:postgresql://localhost:5433/wb_erp_prova``
* Java 11
* Spring versão 2.7.6
* JPA
* Bean Validation
* Maven
* QueryDSL
* REST/JSON

Foi desenvolvida uma aplicação com funcionalidade CRUD para um catalogo de produtos ou serviços e seus respectivos pedidos.
Não há url para item de pedido em função da escolha adotada para os relacionamentps, a saber:
+ o catalogo conhece os itens do pedido: OneToMany;
+ o pedido conhece os itens do pedido: OneToMany;
+ o item do pedido conhece itemPedidoPK: EmbeddedId;
+ a itemPedidoPK conhece o catalogo e o pedido: Embeddable

É possível cadastrar um produto ou serviço no catálogo para incluir em um ou mais pedidos.
Ademais, as regras solicitadas na prova também foram atendidas.

Tembém e possível:
* Listar tudo do catálogo e pedidos
* Listar por Id
* Filtrar no catálogo por ``nome``, ``tipo``, ``condicao``, ``page``, ``linesPerPage``, ``orderBy``, e ``direction``

A sintaxe na url pode ser ``{{localhost}}/produtosservicos/list?nome=Tênis&condicao=ATIVADO``, por exemplo
* Filtrar no pedido por ``status``, ``page``, ``linesPerPage``, ``orderBy``, e ``direction``

A sintaxe na url pode ser ``{{localhost}}/pedidos/list?status=Tênis&condicao=ATIVADO``, por exemplo

* Atualizar item do catálogo, bem como o ``tipo`` e a sua ``condicao``
* Deletar item do catálogo e do pedido e o próprio pedido
* Atualizar o status do pedido
* Atualizar um ou mais itens do pedido
* Incluir mais itens do catálogo no pedido 

### Guia

Para execute digite no diretorio root da aplicação o comando
 * ``maven install``

Com isso as dependências serão baixadas do arquivo ``pom.xml``

Quando rodar a aplicação será craida a base de dados com as tabelas, seus relacionamentos e alguns dados para teste.

Isso será possível somente se o banco de dados existir e a aplicação tiver acesso ao endereço, conforme descrito no arquivo ``application.yml``

    ``url = jdbc:postgresql://localhost:5433/wb_erp_prova``

## Endereços para as funcinalidades
### catálogo

criar no catálogo
    
    ``{{localhost}}/produtosservicos`` - ``POST``
listar tudo do catálogo

    ``{{localhost}}/produtosservicos`` - ``GET``
listar por ID no catálogo

    ``{{localhost}}/produtosservicos/{id}`` - ``GET``
filtro no catálogo

    ``{{localhost}}/produtosservicos/list`` - ``GET``
atualizar no catálogo
    
    ``{{localhost}}/produtosservicos/{id}`` - ``PUT``
delete

    ``{{localhost}}/produtosservicos/list`` - ``DELETE``

### Pedido
criar o pedido

    ``{{localhost}}/pedidos`` - ``POST``
listar tudo do pedido

    ``{{localhost}}/pedidos`` - ``GET``
listar por ID no pedido

    ``{{localhost}}/pedidos/{id}`` - ``GET``
filtro no pedido

    ``{{localhost}}/pedidos/list`` - ``GET``
atualizar no pedido

    ``{{localhost}}/pedidos/{id}`` - ``PUT``
atualizar item no pedido

    ``{{localhost}}/pedidos/item/{id}`` - ``PUT``
incluir itens no pedido

    ``{{localhost}}/pedidos/addItem/{id}`` - ``PUT``
deletar item do pedido

    ``{{localhost}}/pedidos/item/{id}`` - ``DELETE``
deletar o pedido

    ``{{localhost}}/pedidos/{id}`` - ``DELETE``

#### Obs
Na raiz do projeto está o arquivo com as coleções para teste no Postman:

    ``prova-erp.postman_collection.json``

A estrategia do banco está no arquivo ``application.yml`` e pode ser alterada em ``spring.jpa.hibernate.ddl-auto: update`` para ``none``.