package com.matheus.fooddeliveryapi.core.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.info(new Info()
                    .title("Food Delivery API")
                    .version("1.0.0")
                    .description("Documentação da API RESTful para gerenciamento de pedidos de delivery de comida"));

            for (String path : openApi.getPaths().keySet()) {
                PathItem pathItem = openApi.getPaths().get(path);

                applyGlobalResponses(pathItem.getGet(), globalGetResponseMessages());
                applyGlobalResponses(pathItem.getPost(), globalPostResponseMessages());
                applyGlobalResponses(pathItem.getPut(), globalPutResponseMessages());
                applyGlobalResponses(pathItem.getDelete(), globalDeleteResponseMessages());
            }

            configSecurity(openApi);
        };
    }

    private void configSecurity(OpenAPI openApi) {
        openApi.getComponents().addSecuritySchemes("fooddelivery_auth", new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                                .tokenUrl("/oauth/token")
                                .scopes(new Scopes()
                                        .addString("WRITE", "Acesso de escrita")
                                        .addString("READ", "Acesso de leitura")))));

        openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .filter(operation -> !operation.getTags().contains("Host Check"))  // Exclude Host Check
                .forEach(operation -> operation.addSecurityItem(
                        new SecurityRequirement().addList("fooddelivery_auth")));
    }

    private void applyGlobalResponses(Operation operation, Map<String, ApiResponse> responses) {
        if (operation != null) {
            ApiResponses existingResponses = operation.getResponses();

            if (existingResponses == null) {
                existingResponses = new ApiResponses();
            }

            for (Map.Entry<String, ApiResponse> entry : responses.entrySet()) {
                if (!existingResponses.containsKey(entry.getKey())) {
                    existingResponses.addApiResponse(entry.getKey(), entry.getValue());
                }
            }

            operation.setResponses(existingResponses);
        }
    }

    private Map<String, ApiResponse> globalGetResponseMessages() {
        Map<String, ApiResponse> map = new HashMap<>();

        map.put(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                new ApiResponse().description("Erro interno do Servidor")
        );

        map.put(
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                new ApiResponse().description("Recurso não possui representação que pode ser aceita pelo consumidor")
        );

        return map;
    }

    private Map<String, ApiResponse> globalPostResponseMessages() {
        Map<String, ApiResponse> map = getCommonBetweenPostAndPut();

        map.put(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                new ApiResponse().description("Corpo da requisição inválido")
        );

        return map;
    }

    private Map<String, ApiResponse> globalPutResponseMessages() {
        Map<String, ApiResponse> map = getCommonBetweenPostAndPut();

        map.put(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                new ApiResponse().description(
                        "Corpo da requisição inválido ou formato do id inválido(deve ser um número inteiro)"
                )
        );

        return map;
    }

    private Map<String, ApiResponse> getCommonBetweenPostAndPut() {
        Map<String, ApiResponse> map = new HashMap<>();

        map.put(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                new ApiResponse().description("Erro interno no servidor")
        );

        map.put(
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                new ApiResponse()
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
        );

        map.put(
                String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),
                new ApiResponse()
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
        );

        return map;
    }

    private Map<String, ApiResponse> globalDeleteResponseMessages() {
        Map<String, ApiResponse> map = new HashMap<>();

        map.put(
                String.valueOf(HttpStatus.CONFLICT.value()),
                new ApiResponse().description("Entidade está sendo usada e não pode se excluida.")
        );

        map.put(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                new ApiResponse().description("Erro interno no servidor")
        );

        return map;
    }
}
