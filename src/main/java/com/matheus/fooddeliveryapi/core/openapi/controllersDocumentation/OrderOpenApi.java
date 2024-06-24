package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.order.OrderDto;
import com.matheus.fooddeliveryapi.api.model.order.OrderInputDto;
import com.matheus.fooddeliveryapi.api.model.order.OrderResumeDto;
import com.matheus.fooddeliveryapi.domain.filter.OrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

@Tag(name = "Pedidos", description = "Gerencia Pedidos")
public interface OrderOpenApi {

    @Operation(summary = "Pesquisa pedidos", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    Page<OrderResumeDto> getAll(
            @Parameter(description = "Filtro para pesquisa de pedidos") OrderFilter filter,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca um pedido pelo código", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    OrderDto getById(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String code);

    @Operation(summary = "Registra um novo pedido", responses = {
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
            @ApiResponse(responseCode = "400",
                    description = "Corpo da requisição inválido, Restaurante não encontrado, " +
                            "Forma de pagamento não Encontrada, " +
                            "Restaurante não aceita forma de pagamento," +
                            "Endereço inválido, " +
                            "Produto não encontrado, " +
                            "Produto não pertence a esse Restaurante")
    })
    OrderDto add(@Parameter(name = "corpo", description = "Representação de um novo pedido", required = true)
                 @Valid OrderInputDto orderInputDto);
}
