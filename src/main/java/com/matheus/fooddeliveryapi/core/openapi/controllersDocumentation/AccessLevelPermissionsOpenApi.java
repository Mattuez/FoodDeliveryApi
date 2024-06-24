package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Permissões dos níveis de acessos", description = "Associa Permissões aos Níveis de Acesso")
public interface AccessLevelPermissionsOpenApi {

    @Operation(summary = "Lista as permissões de um nível de acesso", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Nível de acesso não encontrado")
    })
    List<PermissionDto> getAllByAccessLevelId(
            @Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId);

    @Operation(summary = "Associa uma permissão a um nível de acesso", responses = {
            @ApiResponse(responseCode = "204", description = "Permissão associada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Nível de acesso ou permissão não encontrado")
    })
    void associate(
            @Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId,
            @Parameter(description = "ID da permissão", example = "2") Long permissionId);

    @Operation(summary = "Desassocia uma permissão de um nível de acesso", responses = {
            @ApiResponse(responseCode = "204", description = "Permissão desassociada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Nível de acesso ou permissão não encontrado")
    })
    void disassociate(
            @Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId,
            @Parameter(description = "ID da permissão", example = "2") Long permissionId);
}
