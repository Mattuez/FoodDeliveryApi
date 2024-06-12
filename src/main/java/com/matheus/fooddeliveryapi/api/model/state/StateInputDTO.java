package com.matheus.fooddeliveryapi.api.model.state;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInputDTO {

    @NotBlank
    private String name;
}
