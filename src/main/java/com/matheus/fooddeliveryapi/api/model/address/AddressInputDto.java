package com.matheus.fooddeliveryapi.api.model.address;

import com.matheus.fooddeliveryapi.api.model.city.CityIdDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInputDto {

    @NotBlank
    private String postalCode;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    private String complement;

    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdDto city;
}
