package com.matheus.fooddeliveryapi.api.model.permission;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PermissionIdDto {

    @NotNull
    private Long id;
}
