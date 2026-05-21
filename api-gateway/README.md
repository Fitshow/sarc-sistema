# API Gateway

Ponto de entrada da plataforma SARC, responsavel por roteamento, seguranca e integracao com os microservicos.

## Responsabilidade

O API Gateway e a entrada unica do front-end para os microservicos:

- `user-service`
- `resource-service`
- `allocation-service`
- `schedule-service`

As rotas usam Spring Cloud Gateway com descoberta via Eureka (`lb://nome-do-servico`).

## Rotas Publicas

Nao exigem token JWT:

- `GET /api/public/**`
- `GET /api/schedules/**`
- `GET /api/resources/public/**`
- `GET /api/allocations/public/**`
- `GET /api/users/professors`

Tambem sao liberadas requisicoes `OPTIONS` para preflight CORS.

## Rotas Protegidas

Exigem role `PROFESSOR` ou `ADMIN`:

- `GET /api/allocations/my`
- `POST /api/allocations/**`
- `PUT /api/allocations/**`
- `DELETE /api/allocations/**`

Exigem role `ADMIN`:

- `POST /api/resources/**`
- `PUT /api/resources/**`
- `PATCH /api/resources/**`
- `DELETE /api/resources/**`
- `GET /api/resources/**`, exceto `/api/resources/public/**`
- `/api/users/**`, exceto `/api/users/professors`

Rotas `POST`, `PUT`, `PATCH` e `DELETE` sob `/api/**` exigem autenticacao como fallback.

## Keycloak

O gateway valida tokens JWT emitidos pelo realm `sarc`.

Roles reconhecidas:

- `PROFESSOR`
- `ADMIN`

A tela publica deve funcionar sem login para visitantes publicos.

No Docker, o issuer publico e:

- `http://localhost:8090/realms/sarc`

E o JWKS interno usado pelos containers fica em:

- `http://keycloak:8080/realms/sarc/protocol/openid-connect/certs`

## CORS

O front-end local esta liberado em:

- `http://localhost:3000`

## Como Testar com Token JWT

Obtenha um token no Keycloak:

```bash
curl -X POST "http://localhost:8090/realms/sarc/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=sarc-web" \
  -d "username=professor@sarc.local" \
  -d "password=123456"
```

Use o `access_token` retornado:

```bash
curl "http://localhost:8080/api/allocations/my" \
  -H "Authorization: Bearer SEU_ACCESS_TOKEN"
```

Para testar uma rota publica:

```bash
curl "http://localhost:8080/api/schedules"
```
