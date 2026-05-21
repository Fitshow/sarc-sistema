# Config Repo

Repositorio local de configuracoes externas consumidas pelo Spring Cloud Config Server.

No ambiente Docker, os servicos usam:

- Eureka em `http://discovery-server:8761/eureka/`
- PostgreSQL em `jdbc:postgresql://postgres:5432/sarc`
- Keycloak com issuer publico `http://localhost:8090/realms/sarc`
- JWKS interno em `http://keycloak:8080/realms/sarc/protocol/openid-connect/certs`
- OpenTelemetry Collector em `http://otel-collector:4318/v1/traces`
