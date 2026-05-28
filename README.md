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

## Processo de Desenvolvimento com IA (SDD)

Este projeto foi desenvolvido utilizando **Specification-Driven Development (SDD)** assistido por IA.

### Ferramentas utilizadas

- **Antigravity (Google DeepMind)** com modelo **Claude Sonnet 4.6 (Thinking)**: assistente de codificacao principal, usado para gerar testes unitarios e de integracao, pipelines CI/CD e refatoracoes.
- **Cursor AI**: suporte a navegacao de codebase e contexto de arquivos abertos.

### Estrategia de prompts

1. **Contexto primeiro**: o assistente leu todos os arquivos de dominio (`domain/`, `service/`, `dto/`) antes de gerar qualquer teste, garantindo aderencia aos tipos e contratos reais.
2. **Cenarios em vez de codigo direto**: os prompts descreviam *o que o teste deveria verificar* (ex.: "deve bloquear email duplicado") e o assistente escrevia os metodos correspondentes.
3. **Revisao manual obrigatoria**: todo codigo gerado pela IA foi revisado antes de ser comitado, verificando coerencia com o dominio, ausencia de mocks desnecessarios e legibilidade.
4. **Iteracao por falha**: ao encontrar o erro de compatibilidade Java 25 / Byte Buddy, o assistente diagnosticou a causa-raiz e aplicou a correcao minima no `pom.xml` sem alterar dependencias.

### Cobertura de testes

| Servico | Testes Controller (MockMvc) | Testes Service (Mockito) | Cenarios totais |
|---|---|---|---|
| `user-service` | 3 | 8 | 11 |
| `resource-service` | 4 | 7 | 11 |
| `allocation-service` | 1 | 5 | 6 |

Cobertura minima configurada: **70% de linhas** via JaCoCo (`mvn verify`).

## Pipelines CI/CD

| Pipeline | Arquivo | Trigger | O que faz |
|---|---|---|---|
| CI — Testes | `.github/workflows/ci.yml` | Push/PR em `main` e `dev` | Compila e testa os 3 servicos com Java 21, publica relatorios JaCoCo |
| Staging | `.github/workflows/staging.yml` | Push em `main` | Empacota os JARs e publica imagens Docker no GHCR com tag `:staging` |

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
