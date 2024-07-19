package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineDTO;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Cozinhas", description = "Gerencia os tipos de cozinha. Ex: italiana, japonesa, brasileira")
public interface CuisineOpenApi {

    @Operation(summary = "Lista as cozinhas", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    ResponseEntity<List<CuisineDTO>> getAll();

    @Operation(summary = "Busca uma cozinha pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido. Deve ser um número inteiro."),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CuisineDTO getById(@Parameter(description = "ID da cozinha", example = "1") Long cuisineId);

    @Operation(summary = "Cadastra uma nova cozinha", responses = {
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada")
    })
    CuisineDTO add(
            @Parameter(name = "corpo", description = "Representação de uma nova cozinha", required = true)
            CuisineInputDTO cuisineInputDTO);

    @Operation(summary = "Atualiza uma cozinha pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CuisineDTO update(
            @Parameter(name = "corpo", description = "Representação da cozinha a ser atualizada", required = true)
            CuisineInputDTO cuisineToBeCopied,
            @Parameter(description = "ID da cozinha", example = "1") Long cuisineId);

    @Operation(summary = "Exclui uma cozinha pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    void remove(@Parameter(description = "ID da cozinha", example = "1") Long cuisineId);
}
