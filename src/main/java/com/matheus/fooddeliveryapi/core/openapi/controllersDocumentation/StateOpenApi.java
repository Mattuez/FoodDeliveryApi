package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.state.StateDTO;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;

import java.util.*;

public class StateOpenApi implements OpenApiCustomiser {

    @Override
    public void customise(OpenAPI openApi) {
        PathItem pathItemStates = new PathItem();
        PathItem pathItemStatesId = new PathItem();

        // Parâmetro de path {stateId}
        Parameter stateIdParameter = new Parameter()
                .name("stateId")
                .in("path")
                .description("ID do estado")
                .required(true)
                .schema(new IntegerSchema().format("int64"));

        // GET /states
        pathItemStates.get(new Operation()
                .summary("Lista todos os estados")
                .description("Retorna uma lista com todos os estados cadastrados.")
                .tags(List.of("States"))
                .responses(createApiResponsesForList(StateDTO.class, "Lista de estados")));

        // POST /states
        pathItemStates.post(new Operation()
                .summary("Cria um novo estado")
                .description("Cria um novo estado com base nos dados fornecidos.")
                .tags(Arrays.asList("States"))
                .requestBody(new RequestBody()
                        .description("Dados do novo estado")
                        .required(true)
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema().$ref("#/components/schemas/StateInput")))))
                .responses(createApiResponses(StateDTO.class, "Estado criado com sucesso")));

        openApi.getPaths().addPathItem("/states", pathItemStates);

        // GET /states/{stateId}
        pathItemStatesId.get(new Operation()
                .summary("Busca um estado por ID")
                .description("Retorna o estado com o ID especificado.")
                .tags(List.of("States"))
                .parameters(List.of(stateIdParameter))
                .responses(createApiResponses(StateDTO.class, "Estado encontrado")));

        // PUT /states/{stateId}
        pathItemStatesId.put(new Operation()
                .summary("Atualiza um estado")
                .description("Atualiza um estado existente com base nos dados fornecidos.")
                .tags(Arrays.asList("States"))
                .parameters(List.of(stateIdParameter))
                .requestBody(new RequestBody()
                        .description("Dados do estado a ser atualizado")
                        .required(true)
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema().$ref("#/components/schemas/StateInput")))))
                .responses(createApiResponses(StateDTO.class, "Estado atualizado com sucesso")));

        // DELETE /states/{stateId}
        pathItemStatesId.delete(new Operation()
                .summary("Exclui um estado")
                .description("Exclui o estado com o ID especificado.")
                .tags(Arrays.asList("States"))
                .parameters(List.of(stateIdParameter))
                .responses(new ApiResponses()
                        .addApiResponse("204", new ApiResponse()
                                .description("Estado excluído com sucesso"))
                        .addApiResponse("404", new ApiResponse()
                                .description("Estado não encontrado"))));

        openApi.getPaths().addPathItem("/states/{stateId}", pathItemStatesId);
    }

    private ApiResponses createApiResponses(Class<?> modelType, String successDescription) {
        return new ApiResponses()
                .addApiResponse("200", new ApiResponse()
                        .description(successDescription)
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema()
                                                .$ref("#/components/schemas/" + modelType.getSimpleName())))))
                .addApiResponse("400", new ApiResponse()
                        .description("Requisição inválida"))
                .addApiResponse("404", new ApiResponse()
                        .description("Recurso não encontrado"))
                .addApiResponse("500", new ApiResponse()
                        .description("Erro interno do servidor"));
    }

    private ApiResponses createApiResponsesForList(Class<?> modelType, String successDescription) {
        return new ApiResponses()
                .addApiResponse("200", new ApiResponse()
                        .description(successDescription)
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new ArraySchema()
                                                .items(new Schema()
                                                        .$ref("#/components/schemas/" + modelType.getSimpleName()))))));
    }
}
