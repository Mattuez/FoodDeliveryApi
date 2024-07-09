package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.city.CityDTO;
import com.matheus.fooddeliveryapi.api.model.city.CityInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Cidades", description = "Gerencia as cidades")
public interface CityOpenApi {

    @Operation(summary = "Lista todas as cidades", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    ResponseEntity<List<CityDTO>> getAll();

    @Operation(summary = "Busca uma cidade pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido. Deve ser um número inteiro."),
            @ApiResponse(responseCode = "404", description = "Cidade não foi encontrada")
    })
    CityDTO getById(@Parameter(description = "ID da cidade", example = "1") Long cityId);

    @Operation(summary = "Cadastra uma nova cidade", responses = {
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada"),
            @ApiResponse(responseCode = "400", description = "Estado não encontrado ou corpo da requisição inválido")
    })
    CityDTO add(
            @Parameter(name = "corpo", description = "Representação de uma nova cidade", required = true)
            CityInputDTO cityInputDTO);

    @Operation(summary = "Atualiza uma cidade pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "400", description = "Estado não encontrado, corpo da requisição inválido " +
                    "ou formato do Id inválido(deve ser um número inteiro)"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })
    CityDTO update(
            @Parameter(name = "corpo", description = "Representação da cidade a ser atualizada", required = true)
            CityInputDTO cityToBeCopied, 
            @Parameter(description = "ID da cidade", example = "1")
            Long cityId);

    @Operation(summary = "Exclui uma cidade pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })
    void remove(@Parameter(description = "ID da cidade", example = "1") Long cityId);
}
