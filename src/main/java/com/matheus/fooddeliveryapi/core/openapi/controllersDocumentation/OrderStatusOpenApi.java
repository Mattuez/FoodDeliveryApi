package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Status Pedido", description = "Gerencia o status de um pedido")
public interface OrderStatusOpenApi {

    @Operation(summary = "Confirma um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser confirmado ou " +
                    "formato do Id inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    void confirm(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String code);

    @Operation(summary = "Entrega um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser entregue ou " +
                    "formato do Id inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    void deliver(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String code);

    @Operation(summary = "Cancela um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser cancelado ou " +
                    "formato do Id inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    void cancel(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String code);
}
