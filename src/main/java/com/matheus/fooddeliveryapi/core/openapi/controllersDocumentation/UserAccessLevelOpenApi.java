package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Permissões de usuários", description = "Associa níveis de permissões a usuários")
public interface UserAccessLevelOpenApi {

    @Operation(summary = "Lista os níveis de acesso de um usuário", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    List<AccessLevelDto> getAllByUserId(@Parameter(description = "ID do usuário", example = "1") Long userId);

    @Operation(summary = "Associa um nível de acesso a um usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Nível de acesso associado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do usuário ou nível de acesso inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário ou nível de acesso não encontrado")
    })
    void associate(@Parameter(description = "ID do usuário", example = "1") Long userId,
                   @Parameter(description = "ID do nível de acesso", example = "2") Long accessLevelId);

    @Operation(summary = "Desassocia um nível de acesso de um usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Nível de acesso desassociado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do usuário ou nível de acesso inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário ou nível de acesso não encontrado")
    })
    void disassociate(@Parameter(description = "ID do usuário", example = "1") Long userId,
                      @Parameter(description = "ID do nível de acesso", example = "2") Long accessLevelId);
}
