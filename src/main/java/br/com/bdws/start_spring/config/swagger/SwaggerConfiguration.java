package br.com.bdws.start_spring.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfiguration {

    @Value("${app-version}")
    private String appVersion;

    @Bean
    public OpenAPI customCofiguration() {
        return new OpenAPI()
            .info(new Info().title("Start Spring")
                .description("Start Spring Study Project API")
                .version(appVersion))
            .externalDocs(new ExternalDocumentation()
                .description("API Documentation"))
            .components(new Components().addSecuritySchemes("bearer-jwt",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER).name("Authorization")))
            .addSecurityItem(
                new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
    }

}
