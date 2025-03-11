package prati.projeto.redeSocial.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
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
                        .description("PI destinada ao gerenciamento de uma rede social para entusiastas de literatura. Esta API oferece recursos para autenticação de usuários, gerenciamento de perfis personalizados, interações sociais e compartilhamento de informações relacionadas ao universo literário. Desenvolvida para promover a conexão entre leitores e o engajamento em uma comunidade dedicada aos livros.")
                        .version("1.0")
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addSecuritySchemes("OAuth2",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("http://localhost:8080/oauth2/authorization/google")
                                                        .tokenUrl("https://oauth2.googleapis.com/token")
                                                        .scopes(new Scopes()
                                                                .addString("email", "Acesso ao email do usuário")
                                                                .addString("profile", "Acesso ao perfil do usuário"))
                                                )
                                        )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .addSecurityItem(new SecurityRequirement().addList("OAuth2"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("prati.projeto.redeSocial.rest.controller")
                .build();
    }
}