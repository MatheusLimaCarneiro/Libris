package prati.projeto.redeSocial.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Back-End: Libris")
                        .description("API destinada ao gerenciamento de uma rede social para entusiastas de literatura. Esta API oferece recursos para autenticação de usuários, gerenciamento de perfis personalizados, interações sociais e compartilhamento de informações relacionadas ao universo literário. Desenvolvida para promover a conexão entre leitores e o engajamento em uma comunidade dedicada aos livros.")
                        .version("1.0")
                       ).components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("prati.projeto.redeSocial.rest.controller")
                .build();
    }
}
