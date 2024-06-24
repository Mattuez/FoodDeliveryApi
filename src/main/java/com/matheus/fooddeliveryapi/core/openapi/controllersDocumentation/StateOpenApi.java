package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.state.StateDTO;
import com.matheus.fooddeliveryapi.api.model.state.StateInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Estados", description = "Gerencia os Estados")
public interface StateOpenApi {

    @Operation(summary = "Lista os estados", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<StateDTO> getAll();

    @Operation(summary = "Busca um estado pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido. Deve ser um número inteiro."),
            @ApiResponse(responseCode = "404", description = "Estado não foi encontrado")
    })
    StateDTO getById(Long stateId);

    @Operation(summary = "Cadastra um estado", responses = {
            @ApiResponse(responseCode = "201", description = "Estado cadastrado")
    })
    StateDTO add(
            @Parameter(name = "corpo", description = "Representação de um novo estado", required = true)
            StateInputDTO stateInput
    );

    @Operation(summary = "Atualiza um estado pelo Id", responses = {
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    })
    StateDTO updateState(
            @Parameter(name = "corpo", description = "Representação de um novo estado", required = true)
            StateInputDTO stateToBeCopied,
            @Parameter(name = "Id", description = "Id de um estado", example = "1", required = true)
            Long stateId
    );

    @Operation(summary = "Exclui um estado por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    })
    void remove(Long stateId);
}
