package com.beeecommerce.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {

    @Value("${info.application.name}")
    private String applicationName;
    
    @Value("${info.application.description}")
    private String applicationDescription;
    
    @Value("${info.application.version}")
    private String applicationVersion;
    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.beeecommerce")
                .pathsToMatch("/**")
                .build();
    }


    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(applicationName)
                        .description(applicationDescription)
                        .version(applicationVersion)

                );
    }


}
