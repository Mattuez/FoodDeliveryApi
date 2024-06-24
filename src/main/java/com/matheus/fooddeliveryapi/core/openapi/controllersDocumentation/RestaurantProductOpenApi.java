package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.products.ProductDto;
import com.matheus.fooddeliveryapi.api.model.products.ProductInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Produtos", description = "Gerencia produtos")
public interface RestaurantProductOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    List<ProductDto> getAllByRestaurantId(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "Incluir produtos inativos?", example = "false") boolean includeInactive);

    @Operation(summary = "Busca um produto de um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID do produto ou do " +
                    "restaurante inválido(deve ser um número inteiro)"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ProductDto getById(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "ID do produto", example = "2") Long productId);

    @Operation(summary = "Cadastra um novo produto em um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    ProductDto add(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(name = "corpo", description = "Representação de um novo produto", required = true)
            ProductInputDto source);

    @Operation(summary = "Atualiza um produto de um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ProductDto update(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "ID do produto", example = "2") Long productId,
            @Parameter(name = "corpo", description = "Representação do produto a ser atualizado", required = true)
            ProductInputDto source);
}
