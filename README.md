# SARC Arquitetura Projeto

Monorepo base para a revitalizacao do SARC, uma plataforma institucional para consulta e alocacao de salas, laboratorios e recursos computacionais em datas e horarios especificos.

## Arquitetura

- Microservicos com Java 21, Spring Boot e Spring Cloud
- API Gateway com Spring Cloud Gateway
- Eureka Discovery Server
- Spring Cloud Config Server
- Autenticacao e autorizacao com Keycloak e OpenID Connect
- PostgreSQL por servico, a ser detalhado em etapa posterior
- Observabilidade com OpenTelemetry
- Front-end com React, Vite e TypeScript
- Docker Compose para ambiente local

## Modulos

- `discovery-server`: servidor Eureka
- `config-server`: servidor Spring Cloud Config
- `api-gateway`: gateway de entrada da plataforma
- `user-service`: servico de usuarios e perfis
- `resource-service`: servico de salas, laboratorios e recursos
- `allocation-service`: servico de alocacoes
- `schedule-service`: servico de horarios e disponibilidade
- `sarc-web`: aplicacao web React
- `config-repo`: configuracoes externas dos servicos
- `database`: scripts SQL do modelo relacional PostgreSQL
- `docs`: documentacao do projeto
- `docker`: arquivos de infraestrutura local
- `scripts`: scripts auxiliares

## Status

Estrutura inicial criada com modelo relacional e infraestrutura Docker base. Regras de negocio e interface completa ainda serao implementadas em etapas posteriores.

## Execucao Local com Docker

Na raiz do projeto, execute:

```bash
docker-compose up --build
```

Ou, usando o Docker Compose plugin:

```bash
docker compose up --build
```

Servicos principais:

- Front-end: http://localhost:3000
- API Gateway: http://localhost:8080
- Eureka: http://localhost:8761
- Config Server: http://localhost:8888
- Keycloak: http://localhost:8090
- PostgreSQL: localhost:5432

Keycloak:

- Realm: `sarc`
- Roles: `PROFESSOR`, `ADMIN`
- Usuario professor: `professor@sarc.local` / `123456`
- Usuario administrador: `admin@sarc.local` / `123456`

SonarQube esta documentado como opcional no `docker-compose.yml` da raiz.
