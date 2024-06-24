package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Restaurantes", description = "Gerencia os Restaurantes")
public interface RestaurantOpenApi {

    @Operation(summary = "Lista os restaurantes", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<RestaurantDTO> getAll();

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    RestaurantDTO getById(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Cadastra um novo restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido ou cozinha/cidade não encontrada")
    })
    RestaurantDTO add(@Parameter(name = "corpo", description = "Representação de um novo restaurante", required = true)
                      RestaurantInputDTO restaurantInputDTO);

    @Operation(summary = "Atualiza um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido, " +
                    "cozinha/cidade não encontrada ou formato do ID inválido(deve ser um número inteiro)"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    RestaurantDTO update(@Parameter(name = "corpo", description = "Representação do restaurante a ser atualizado", required = true)
                          RestaurantInputDTO restaurantToBeCopied,
                          @Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Exclui um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    void remove(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Ativa um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    void activate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Desativa um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante desativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    void deactivate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Ativa uma lista de restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void activateCollection(@Parameter(name = "corpo", description = "IDs dos restaurantes a serem ativados", required = true) List<Long> restaurantIds);

    @Operation(summary = "Desativa uma lista de restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes desativados com sucesso")
    })
    void deactivateCollection(@Parameter(name = "corpo", description = "IDs dos restaurantes a serem desativados", required = true) List<Long> restaurantIds);

    @Operation(summary = "Abre um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    void opened(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Fecha um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    void closed(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);
}
