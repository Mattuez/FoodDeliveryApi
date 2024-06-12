package com.matheus.fooddeliveryapi.api.model.accessLevel;

import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessLevelDto {

    private Long id;
    private String name;
    private List<PermissionDto> permissions;
}
