package br.edu.sarc.resource.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI resourceServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SARC Resource Service API")
                        .description("API de recursos, salas, laboratorios e equipamentos do SARC.")
                        .version("0.0.1"))
                .schemaRequirement("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }
}
