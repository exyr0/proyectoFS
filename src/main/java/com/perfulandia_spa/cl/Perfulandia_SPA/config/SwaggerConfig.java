package com.perfulandia_spa.cl.Perfulandia_SPA.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Perfulandia SPA")
                .version("v1.0")
                .description("Documentacion para el sistema de compra de Perfulandia SPA"));
    }
}