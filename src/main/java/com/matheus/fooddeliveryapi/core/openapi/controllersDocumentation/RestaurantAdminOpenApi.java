package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Administradores de Restaurante", description = "Gerencia os administradores de um restaurante")
public interface RestaurantAdminOpenApi {

    @Operation(summary = "Lista os administradores de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    List<UserDto> getAllByRestaurantId(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Associa um administrador a um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Administrador associado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID do restaurante ou " +
                    "usuário invalido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado")
    })
    void associate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
                   @Parameter(description = "ID do usuário", example = "2") Long userId);

    @Operation(summary = "Desassocia um administrador de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Administrador desassociado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID do restaurante ou " +
                    "usuário invalido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado")
    })
    void disassociate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
                      @Parameter(description = "ID do usuário", example = "2") Long userId);
}
