# Banco de Dados

Scripts SQL iniciais para o modelo relacional PostgreSQL do SARC.

## Arquivos

- `01_schema.sql`: cria as tabelas, chaves primarias, chaves estrangeiras e constraints principais.
- `02_seed.sql`: insere dados iniciais de usuarios, recursos e alocacoes.

## Ordem de execucao

1. Execute `00_create_database.sql` conectado ao banco administrativo `postgres`.
2. Conecte-se ao banco `sarc`.
3. Execute `01_schema.sql`.
4. Execute `02_seed.sql`.
