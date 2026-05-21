# SARC Web

Aplicacao web base em React, Vite e TypeScript para a plataforma SARC.

## Funcionalidades

- Consulta publica de grades em `/`
- Login institucional em `/login` via Keycloak
- Area do professor em `/professor`
- Administracao em `/admin`
- Gerenciamento de recursos no painel administrativo

Visitantes publicos usam apenas a consulta publica.

## Variaveis de Ambiente

- `VITE_API_BASE_URL`: URL do API Gateway. Padrao: `http://localhost:8080`
- `VITE_KEYCLOAK_URL`: URL do Keycloak. Padrao: `http://localhost:8090`
- `VITE_KEYCLOAK_REALM`: realm do Keycloak. Padrao: `sarc`
- `VITE_KEYCLOAK_CLIENT_ID`: client publico. Padrao: `sarc-web`

## Comandos

```bash
npm install
npm run build
npm run dev
```
