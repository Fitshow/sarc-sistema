# ADR 0001 - Microservicos com Spring Cloud

## Status

Aceita.

## Contexto

O SARC precisa separar responsabilidades de usuarios, recursos, alocacoes, grade publica e entrada unica para o front-end.

## Decisao

Usar uma arquitetura de microservicos com Spring Boot, Spring Cloud Gateway, Eureka Discovery Server e Spring Cloud Config Server.

## Consequencias

- Cada dominio evolui de forma independente.
- O API Gateway centraliza seguranca e roteamento.
- O Config Server centraliza configuracoes por servico.
- A operacao local depende de infraestrutura Docker coordenada.
