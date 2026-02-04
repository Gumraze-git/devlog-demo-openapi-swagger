package com.gumraze.opanapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenAPI/Swagger Demo")          // swagger ui 제목
                        .version("1.0")                         // 버전
                        .description("Spring Boot + springdoc-openapi demo")  // 설명
                        // 연락처 입력
                        .contact(new Contact()
                                .name("Gumraze")
                                .email("galaxydh4110@gmail.com")
                                .url("https://gumraze.com")
                        )
                        // 라이선스 입력
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                )
                // 서버 정보 명시
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local Server")
                )
                // Bearer Token(JWT) 보안 설정
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                ;
    }
}
