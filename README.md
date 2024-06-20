# Spring boot Web Service

Link: [https://webservice-spring.helielsouza.com.br/home](https://webservice-spring.helielsouza.com.br/home)

### (pt-br)
## O que é:
Essa é uma RESTful Webservice relacionada a vendas de uma loja on-line, no qual ela permite trabalhar com diversas entidades: usuários, pedidos, pagamentos, produtos, categorias e entre outros.

## Tecnologias utilizadas no desenvolvimento/testes:
- **Spring boot 3**: Framework utilizado para facilitar o desenvolvimento de aplicações Java, automatizando configurações e inicializações.
- **JPA**: Interface de mapeamento objeto-relacional que define padrões para persistência de dados em Java.
- **Hibernate**: Implementação da JPA que gerencia a persistência de dados em bancos de dados relacionais.
- **Spring Security**: Framework para autenticação e autorização de usuários em aplicações Java com JWT.
- **H2 Database**: Banco de dados em memória rápido e leve usado para testes e desenvolvimento.
- **Apache Tomcat**: Servidor web e contêiner de servlets para rodar aplicações Java.
- **Maven**: Utilizado para o gerenciamento de dependências e automatização de build. Foi responsável também em gerar o projeto em JAR para ser enviado para o servidor.
- **Arquitetura de Camadas do Spring**: Estrutura que separa lógica de apresentação, negócios e acesso a dados, promovendo organização e manutenibilidade. As camadas: recursos (controllers), serviços (services), entidades (entities) e persistência (repository).

## Tecnologias utilizadas na implantação:
- **Docker**: Plataforma que facilita a criação, deploy e execução de aplicações em contêineres isolados.
- **Nginx**: Servidor web e proxy reverso utilizado para balanceamento de carga, cache e segurança.
- **VPS Ubuntu**: Servidor Virtual Privado em Linux escolhido para hospedagem do WebService.
- **Certificado SSL**: Protocolo de segurança que criptografa a comunicação entre cliente e servidor, gerado para garantir a proteção dos dados transmitidos. Isso garante o acesso via HTTPS.
- **PostgreSQ**L: Sistema de gerenciamento de banco de dados relacional open-source, robusto e escalável.

## Como funciona:
Envie requisições HTTP para as endpoints públicas:
- **GET**: [/users](https://webservice-spring.helielsouza.com.br/users) - Para retornar todos os usuários via JSON (com senha encriptada);
- **GET**: [/users/1](https://webservice-spring.helielsouza.com.br/users/1) - Para retornar um usuário pelo ID (pode ser qualquer ID) via JSON;
- **GET**: [/orders](https://webservice-spring.helielsouza.com.br/orders) - Para retornar todos os pedidos via JSON;
- **GET**: [/orders/1](https://webservice-spring.helielsouza.com.br/orders/1) - Para retornar um pedido pelo ID (pode ser qualquer ID) via JSON;
- **GET**: [/products](https://webservice-spring.helielsouza.com.br/products) - Para retornar todos os produtos via JSON;
- **GET**: [/products/1](https://webservice-spring.helielsouza.com.br/products/1) - Para retornar um produto pelo ID (pode ser qualquer ID) via JSON;
- **GET**: [/categories](https://webservice-spring.helielsouza.com.br/categories) - Para retornar todas as categorias via JSON;
- **GET**: [/categories/1](https://webservice-spring.helielsouza.com.br/categories/1) - Para retornar uma categoria pelo ID (pode ser qualquer ID) via JSON;
- **POST**, **PUT**, **DELETE**: Todas as endpoints acima requerem autenticação para esses métodos.


Para poder se autenticar, faça uma requisição **POST** na endpoint [/auth/signin](https://webservice-spring.helielsouza.com.br/auth/singin) passando dados de *username* e *password* de algum usuário ADMIN (Isso não será disponilizado):

![signin-post](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/f2cd6c61-f502-4892-bf4c-b12214fc326f)



Serão retornados os dados JSON do access_token e refresh_token após a requisição acima ser conclúida:

![access-and-refresh-tokens](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/2edf7821-c669-4089-a182-3ff271cfb9e9)



Esses dados devem ser devem ser passados no HEADER das requisições que possuem restrição de autenticação igual à forma abaixo:

![header-access-token](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/6add3f47-8f83-48a3-815f-9483b91936c8)



Com o cliente passando o access-token desta forma, ele poderá realizar requisições com os métodos HTTP restritos, sendo eles **POST**, **PUT**, **DELETE** para todas as entidades acima, além também é claro, do método **GET**.

O access_token tem prazo de validade, para isso, será necessário fazer uma requisição **PUT** para a endpoint [auth/refresh/username](https://webservice-spring.helielsouza.com.br/auth/refresh/<username>) sendo *<username>* o nome de usuário do usuário recém-autenticado:

![header-refresh-token](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/b5886b2a-3d39-4227-84e5-0ce894559737)

Com isso, um novo access_token será gerado e poderá ser atualizado, para que as requisições para endpoints restritas possam continuar sendo realizadas com sucesso.


### (en-us)
## What it is:
This is a RESTful WebService related to sales of an online store, which allows working with various entities: users, orders, payments, products, categories, and more.

## Technologies used in development/testing:
- **Spring Boot 3**: Framework used to facilitate the development of Java applications by automating configurations and initializations.
- **JPA**: Object-relational mapping interface that defines standards for data persistence in Java.
- **Hibernate**: JPA implementation that manages data persistence in relational databases.
- **Spring Security**: Framework for authentication and authorization of users in Java applications with JWT.
- **H2 Database**: Fast and lightweight in-memory database used for testing and development.
- **Apache Tomcat**: Web server and servlet container for running Java applications.
- **Maven**: Used for dependency management and build automation. It was also responsible for generating the project in JAR to be sent to the server.
- **Spring Layered Architecture**: Structure that separates presentation, business, and data access logic, promoting organization and maintainability. The layers: resources (controllers), services (services), entities (entities), and persistence (repository).

## Technologies used in deployment:
- **Docker**: Platform that facilitates the creation, deployment, and execution of applications in isolated containers.
- **Nginx**: Web server and reverse proxy used for load balancing, caching, and security.
- **VPS Ubuntu**: Virtual Private Server on Linux chosen for hosting the WebService.
- **SSL Certificate**: Security protocol that encrypts the communication between client and server, generated to ensure the protection of transmitted data. This ensures access via HTTPS.
- **PostgreSQL**: Open-source, robust, and scalable relational database management system.

## How it works:
Send HTTP requests to the public endpoints:

- **GET**: [/users](https://webservice-spring.helielsouza.com.br/users) - To return all users via JSON (with encrypted password);
- **GET**: [/users/1](https://webservice-spring.helielsouza.com.br/users/1) - To return a user by ID (it can be any ID) via JSON;
- **GET**: [/orders](https://webservice-spring.helielsouza.com.br/orders) - To return all orders via JSON;
- **GET**: [/orders/1](https://webservice-spring.helielsouza.com.br/orders/1) - To return an order by ID (it can be any ID) via JSON;
- **GET**: [/products](https://webservice-spring.helielsouza.com.br/products) - To return all products via JSON;
- **GET**: [/products/1](https://webservice-spring.helielsouza.com.br/products/1) - To return a product by ID (it can be any ID) via JSON;
- **GET**: [/categories](https://webservice-spring.helielsouza.com.br/categories) - To return all categories via JSON;
- **GET**: [/categories/1](https://webservice-spring.helielsouza.com.br/categories/1) - To return a category by ID (it can be any ID) via JSON;
- **POST**, **PUT**, **DELETE**: All the above endpoints require authentication for these methods.

To authenticate, make a **POST** request to the endpoint [/auth/signin](https://webservice-spring.helielsouza.com.br/auth/singin) passing the *username* and *password* of an ADMIN user (this will not be provided):

![signin-post](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/f2cd6c61-f502-4892-bf4c-b12214fc326f)


The JSON data of the access_token and refresh_token will be returned after the above request is completed:

![access-and-refresh-tokens](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/2edf7821-c669-4089-a182-3ff271cfb9e9)


These data must be passed in the HEADER of the requests that require authentication as shown below:

![header-access-token](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/6add3f47-8f83-48a3-815f-9483b91936c8)


With the client passing the access-token this way, they can make requests with the restricted HTTP methods, which are **POST**, **PUT**, **DELETE** for all the entities above, as well as, of course, the **GET** method.

The access_token has an expiration date, so it will be necessary to make a PUT request to the endpoint [auth/refresh/username](https://webservice-spring.helielsouza.com.br/auth/refresh/<username>) where *<username>* is the username of the newly authenticated user:

![header-refresh-token](https://github.com/HelielSouza/Springboot3-JPA-Workshop/assets/127799256/b5886b2a-3d39-4227-84e5-0ce894559737)


With this, a new access_token will be generated and can be updated so that requests to restricted endpoints can continue to be made successfully.
