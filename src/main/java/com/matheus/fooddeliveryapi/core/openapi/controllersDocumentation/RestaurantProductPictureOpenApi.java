package com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation;

import com.matheus.fooddeliveryapi.api.model.products.picture.ProductPictureDto;
import com.matheus.fooddeliveryapi.api.model.products.picture.ProductPictureInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = "Restaurantes")
public interface RestaurantProductPictureOpenApi {

    @Operation(summary = "Obtém os metadados da foto do produto", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Foto do produto não encontrada")
    })
    ProductPictureDto getPictureData(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "ID do produto", example = "2") Long productId);

    @Operation(summary = "Obtém a foto do produto", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = {
                            @Content(mediaType = "image/jpeg"),
                            @Content(mediaType = "image/png")
                    }),
            @ApiResponse(responseCode = "404", description = "Foto do produto não encontrada"),
            @ApiResponse(responseCode = "406", description = "Formato de mídia não aceito")
    })
    ResponseEntity<InputStreamResource> getPicture(@Parameter(description = "Id do Restaurante", example = "1") Long restaurantId,
                                                   @Parameter(description = "Id do Restaurante", example = "1") Long productId,
                                                   @Parameter(name = "accept", description = "Foto do produto", in = ParameterIn.HEADER, example = "image/jpeg") String acceptedMediaType)
            throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Atualiza a foto do produto", responses = {
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ProductPictureDto updatePicture(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "ID do produto", example = "2") Long productId,
            @Parameter(name = "corpo", description = "Nova foto do produto",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = ProductPictureInputDto.class)), required = true)
            ProductPictureInputDto inputProductPicture) throws IOException;

    @Operation(summary = "Exclui a foto do produto", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "404", description = "Foto do produto não encontrada")
    })
    void deletePicture(
            @Parameter(description = "ID do restaurante", example = "1") Long restaurantId,
            @Parameter(description = "ID do produto", example = "2") Long productId);
}
