package br.edu.sarc.schedule.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI scheduleServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SARC Schedule Service API")
                        .description("API publica de grade consolidada do SARC. Visitantes acessam informacoes sem dados sensiveis.")
                        .version("0.0.1"));
    }
}
