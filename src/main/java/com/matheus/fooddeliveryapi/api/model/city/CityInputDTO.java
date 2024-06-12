package com.matheus.fooddeliveryapi.api.model.city;

import com.matheus.fooddeliveryapi.api.model.state.StateIdDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputDTO {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdDTO state;
}
