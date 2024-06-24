package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.user.UserAddInputDto;
import com.matheus.fooddeliveryapi.api.model.user.UserChangePasswordDto;
import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import com.matheus.fooddeliveryapi.api.model.user.UserUpdateInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Usuários", description = "Gerencia Usuários")
public interface UserOpenApi {

    @Operation(summary = "Lista todos os usuários", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    List<UserDto> getAll();

    @Operation(summary = "Busca um usuário pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    UserDto getById(@Parameter(description = "ID do usuário", example = "1") Long userId);

    @Operation(summary = "Adiciona um novo usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário adicionado")
    })
    UserDto add(
            @Parameter(name = "corpo", description = "Representação de um novo usuário", required = true)
            UserAddInputDto userInputDto);

    @Operation(summary = "Atualiza um usuário por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    UserDto update(
            @Parameter(description = "ID do usuário", example = "1") Long userId,
            @Parameter(name = "corpo", description = "Representação do usuário a ser atualizado", required = true)
            UserUpdateInputDto source);

    @Operation(summary = "Remove um usuário por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Usuário removido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    void remove(@Parameter(description = "ID do usuário", example = "1") Long userId);

    @Operation(summary = "Altera a senha do usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "As senhas não coincidem"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    void changePassword(
            @Parameter(description = "ID do usuário", example = "1") Long userId,
            @Parameter(name = "corpo", description = "Antiga e nova senha", required = true)
            UserChangePasswordDto userChangePasswordDto);
}

