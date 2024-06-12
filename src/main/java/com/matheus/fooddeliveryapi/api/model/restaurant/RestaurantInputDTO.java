package com.matheus.fooddeliveryapi.api.model.restaurant;

import com.matheus.fooddeliveryapi.api.model.address.AddressInputDto;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineIdDTO;
import com.matheus.fooddeliveryapi.core.validation.annotation.DeliveryFee;
import com.matheus.fooddeliveryapi.core.validation.annotation.ZeroValueIncludesDesc;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ZeroValueIncludesDesc(
        fieldValue = "deliveryFee",
        fieldDesc = "name",
        requiredDesc = "free delivery"
)
public class RestaurantInputDTO {

    @NotBlank
    private String name;

    @DeliveryFee
    private BigDecimal deliveryFee;

    @Valid
    @NotNull
    private CuisineIdDTO cuisine;

    @NotNull
    @Valid
    private AddressInputDto address;
}
