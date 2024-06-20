# Spring boot Web Service

Link: https://webservice-spring.helielsouza.com.br/home

## (pt-br)
## O que é
Essa é uma RESTful Webservice relacionada a vendas de uma loja on-line, no qual ela permite trabalhar com diversas entidades: usuários, pedidos, pagamentos, produtos, categorias e tudo mais

## Como funciona
Endpoints públicas
GET: https://webservice-spring.helielsouza.com.br/users - Para retornar todos os usuários via JSON (com senha encriptada);
GET: https://webservice-spring.helielsouza.com.br/users/1 - Para retornar um usuário pelo ID (pode ser qualquer ID) via JSON;
GET: https://webservice-spring.helielsouza.com.br/orders - Para retornar todos os pedidos via JSON;
GET: https://webservice-spring.helielsouza.com.br/orders/1 - Para retornar um pedido pelo ID (pode ser qualquer ID) via JSON;
GET: https://webservice-spring.helielsouza.com.br/products - Para retornar todos os produtos via JSON;
GET: https://webservice-spring.helielsouza.com.br/products/1 - Para retornar um produto pelo ID (pode ser qualquer ID) via JSON;
GET: https://webservice-spring.helielsouza.com.br/categories - Para retornar todas as categorias via JSON;
GET: https://webservice-spring.helielsouza.com.br/categories/1 - Para retornar uma categoria pelo ID (pode ser qualquer ID) via JSON
PUT, DELETE, POST: Todas as endpoints acima requerem autenticação para esses métodos.


Para poder se autenticar, faça uma requisição POST na endpoint /auth/signin passando esses username e password de algum usuário ADMIN (Isso não será disponilizado)


