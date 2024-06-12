package com.matheus.fooddeliveryapi.api.model.accessLevel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccessLevelInputDto {

    @NotBlank
    private String name;
}
