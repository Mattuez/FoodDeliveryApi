package com.matheus.fooddeliveryapi.api.model.permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDto {

    private Long id;
    private String name;
    private String description;
}
