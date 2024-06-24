package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Formas de Pagamento", description = "Gerencia as Formas de Pagamento")
public interface PaymentMethodOpenApi {

    @Operation(summary = "Lista as formas de pagamento", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<PaymentMethodDto> getAll();

    @Operation(summary = "Busca uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato do ID inválido(deve ser um número inteiro)."),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    PaymentMethodDto getById(@Parameter(description = "ID da forma de pagamento", example = "1") Long paymentMethodId);

    @Operation(summary = "Cadastra uma nova forma de pagamento", responses = {
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")
    })
    PaymentMethodDto add(
            @Parameter(name = "corpo", description = "Representação de uma nova forma de pagamento", required = true)
            PaymentMethodInputDto paymentMethodInputDto);

    @Operation(summary = "Atualiza uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    PaymentMethodDto update(
            @Parameter(description = "ID da forma de pagamento", example = "1") Long paymentMethodId,
            @Parameter(name = "corpo", description = "Representação da forma de pagamento a ser atualizada", required = true)
            PaymentMethodInputDto paymentMethodInputDto);

    @Operation(summary = "Exclui uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    void remove(@Parameter(description = "ID da forma de pagamento", example = "1") Long paymentMethodId);
}
