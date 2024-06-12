package com.matheus.fooddeliveryapi.api.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserChangePasswordDto {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
