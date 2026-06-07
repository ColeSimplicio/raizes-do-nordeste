# Raízes do Nordeste API

## Repositório

```
https://github.com/ColeSimplicio/raizes-do-nordeste
```


## Descrição

API backend para gerenciamento de restaurante, contendo módulos de autenticação, produtos, cardápio, unidades, estoque, pedidos e pagamentos.

## Ambiente pronto para avaliação (IMPORTANTE)

A aplicação já possui seed automático via Flyway, com dados iniciais populados para permitir execução imediata do sistema sem necessidade de cadastro manual.

Ao iniciar o projeto, o banco já contém:

* Usuário administrador padrão
* Usuário cliente de teste
* Unidade de restaurante pré-configurada (Raízes do Nordeste - Fortaleza)
* Produtos típicos do Nordeste (ex: Acarajé, Baião de Dois, Carne de Sol, etc.)
* Cardápio completo associado à unidade
* Estoque inicial disponível para todos os produtos
* Itens de cardápio com preços definidos


## Requisitos

* Java 17
* Spring Boot 3.5.14
* Maven 3+
* PostgreSQL
* Flyway (migrations automáticas)
* Spring Security + JWT

## Auditorias
Os registros de auditoria são informações administrativas e de segurança. Neste projeto eles são armazenados para rastreabilidade e análise dos pedidos criados e cancelados, podendo ser futuramente implementados para outras ações sensíveis do sistema com facilidade.

## Promoções e descontos
Promoções podem ser aplicadas através de
descontos no valorTotal do pedido, na
alteração temporária de preços no ItemCardapio, existindo também a possibilidade da criação de itens sazonais de acordo com a época do ano.

## LGPD e consentimento do uso de dados e finalidade
Atualmente não são coletados dados sensíveis do usuário, sendo informados apenas o nome, email, e senha (armazenada com hash no banco de dados), com a finalidade de autenticar e identificar o usuário, e possibilitar
sua adesão ao programa de fidelidade.


## Banco de dados

### Criar banco PostgreSQL

```sql
CREATE DATABASE raizes_do_nordeste;
```

## Configuração de ambiente

A aplicação utiliza variáveis de ambiente para credenciais do banco.

### application.properties

```
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### Exemplo de .env (ou variáveis do sistema)

Crie um arquivo `.env.example`:

```
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```


## Instalação de dependências

```bash
mvn clean install
```

## Execução da aplicação

```bash
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

## Swagger (Documentação da API)

Após iniciar a aplicação, acessar:

```
http://localhost:8080/swagger-ui/index.html
```

## Autenticação (Seed inicial)

O sistema já inicia com um usuário administrador criado via migration.

### Admin padrão

```
Email: admin@gmail.com  
Senha: senhasecreta
```

Esse usuário deve ser utilizado para operações administrativas.

### Cliente para testes

```
Email: cliente@gmail.com  
Senha: 123456
```

## Coleção de testes 

A coleção de testes está disponível no repositório:

```
/docs/raizes-do-nordeste.postman_collection.json
```

Ela contém requisições organizadas por módulos:

* Autenticação
* Produtos
* Cardápio
* Unidades
* Estoque
* Pedidos
* Pagamento
* Auditoria

## Como executar os testes (ordem sugerida)

### 1. Autenticação como CLIENTE

```
POST /auth/login
```

Credenciais:

```
Email: cliente@gmail.com  
Senha: 123456
```

Copiar o token JWT retornado.

### 2. Consultar cardápio (Token JWT)

```
GET /unidades/{unidadeId}/cardapio
```

### 3. Fazer pedido 

```
POST /pedidos
```
O pedido valida automaticamente:

* disponibilidade no cardápio
* estoque
* regras de negócio
* pontos de fidelidade (se aplicável)

### 4. Aprovar ou recusar pagamento mock (ADMIN)

Logar como admin:

```
POST /auth/login
```

Credenciais:

```
Email: admin@gmail.com  
Senha: senhasecreta
```

Copiar token JWT.

```
POST /pagamentos/pedido/{id}
```


### 5. Consultar status (Pode ser consultado pelo ADMIN ou pelo CLIENTE que realizou pedido)

```
GET /pedidos/{id}/status
```


## 6. Consultar registro de ações sensíveis (ADMIN)

```
GET /auditorias
```


