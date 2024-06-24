package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Permissões", description = "Gerencia as permissões")
public interface PermissionOpenApi {

    @Operation(summary = "Lista todas as permissões", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<PermissionDto> getAll();

    @Operation(summary = "Busca uma permissão pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido. Deve ser um número inteiro."),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada")
    })
    PermissionDto getById(@Parameter(description = "ID da permissão", example = "1") Long permissionId);
}
