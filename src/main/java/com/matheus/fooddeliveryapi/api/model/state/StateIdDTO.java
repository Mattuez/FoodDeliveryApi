package com.matheus.fooddeliveryapi.api.model.state;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class StateIdDTO {
    @NotNull
    private Long id;
}
