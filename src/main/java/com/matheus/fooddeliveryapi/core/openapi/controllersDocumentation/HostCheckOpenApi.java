package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.UnknownHostException;

@Tag(name = "Host Check", description = "Endpoint para verificação de host")
public interface HostCheckOpenApi {
    
    @Operation(summary = "Retorna o endereço IP e nome do host", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    String checkHost() throws UnknownHostException;
}

