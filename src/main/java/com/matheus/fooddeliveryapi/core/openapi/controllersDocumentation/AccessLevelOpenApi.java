package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Níveis de Acesso (Cargo)", description = "Gerencia os Níveis de Acesso")
public interface AccessLevelOpenApi {

    @Operation(summary = "Lista todos os níveis de acesso", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<AccessLevelDto> getAll();

    @Operation(summary = "Busca um nível de acesso pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Nível de acesso não encontrado")
    })
    AccessLevelDto getById(@Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId);

    @Operation(summary = "Cria um novo nível de acesso", responses = {
            @ApiResponse(responseCode = "201", description = "Nível de acesso criado")
    })
    AccessLevelDto add(
            @Parameter(name = "corpo", description = "Representação de um novo nível de acesso", required = true)
            AccessLevelInputDto accessLevelInputDto);

    @Operation(summary = "Atualiza um nível de acesso por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Nível de acesso atualizado"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "404", description = "Nível de acesso não encontrado")
    })
    AccessLevelDto update(
            @Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId,
            @Parameter(name = "corpo", description = "Representação do nível de acesso a ser atualizado", required = true)
            AccessLevelInputDto accessLevelInputDto);

    @Operation(summary = "Exclui um nível de acesso por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Nível de acesso excluído"),
            @ApiResponse(responseCode = "404", description = "Nível de acesso não encontrado")
    })
    void remove(@Parameter(description = "ID do nível de acesso", example = "1") Long accessLevelId);
}
