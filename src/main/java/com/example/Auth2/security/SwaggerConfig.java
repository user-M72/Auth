package com.example.Auth2.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(description = "Local ENV", url = "http://localhost:8080")})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Authentication").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("AuthenticationSecurityScheme"))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "AuthenticationSecurityScheme",
                                        new SecurityScheme()
                                                .bearerFormat("JWT")
                                                .name("bearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .in(SecurityScheme.In.HEADER)));
    }
}
