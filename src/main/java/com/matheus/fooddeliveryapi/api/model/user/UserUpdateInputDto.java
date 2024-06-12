package com.matheus.fooddeliveryapi.api.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateInputDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
