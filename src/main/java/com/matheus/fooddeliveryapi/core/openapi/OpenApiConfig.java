package com.matheus.fooddeliveryapi.core.openapi;

import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.StateOpenApi;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.info(new Info()
                    .title("Food-Delivery-API")
                    .version("1.0.0")
                    .description("Documentação da API RESTful para gerenciamento de pedidos de delivery de comida"));

            openApi.tags(Arrays.asList(
                    new Tag().name("States").description("Endpoints relacionados a estados")
            ));
        };
    }

    @Bean
    public StateOpenApi stateOpenApi() {
        return new StateOpenApi();
    }
}