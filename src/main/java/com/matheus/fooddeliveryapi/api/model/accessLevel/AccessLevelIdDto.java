package com.matheus.fooddeliveryapi.api.model.accessLevel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccessLevelIdDto {

    @NotNull
    private Long id;
}
