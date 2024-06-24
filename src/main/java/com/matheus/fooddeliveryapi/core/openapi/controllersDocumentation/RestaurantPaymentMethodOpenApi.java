package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Formas de Pagamento de Restaurantes", description = "Gerencia as formas de pagamento de um restaurante")
public interface RestaurantPaymentMethodOpenApi {

    @Operation(summary = "Lista as formas de pagamento de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    List<PaymentMethodDto> getAllByRestaurantId(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId);

    @Operation(summary = "Associa uma forma de pagamento a um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Forma de pagamento associada com sucesso"),
            @ApiResponse(responseCode = "400", description = "formato do id inválido(deve ser um número inteiro)"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado")
    })
    void associate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
                   @Parameter(description = "ID da forma de pagamento", example = "2") Long paymentMethodId);

    @Operation(summary = "Desassocia uma forma de pagamento de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada com sucesso"),
            @ApiResponse(responseCode = "400", description = "formato do id inválido(deve ser um número inteiro)"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado")
    })
    void disassociate(@Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
                      @Parameter(description = "ID da forma de pagamento", example = "2") Long paymentMethodId);
}

