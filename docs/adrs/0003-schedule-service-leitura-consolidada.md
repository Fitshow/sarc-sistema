# ADR 0003 - Schedule Service com Leitura Consolidada

## Status

Aceita.

## Contexto

A tela publica precisa consultar a grade com baixa complexidade para o front-end, exibindo apenas dados nao sensiveis.

## Decisao

O `schedule-service` atua como camada publica de leitura consolidada sobre as tabelas de usuarios, recursos e alocacoes.

## Consequencias

- Regras de escrita permanecem nos servicos de dominio.
- A resposta publica nao expoe dados sensiveis.
- O front-end consome uma API otimizada para grade e filtros.
