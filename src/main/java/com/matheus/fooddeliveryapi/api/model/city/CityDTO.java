package com.matheus.fooddeliveryapi.api.model.city;

import com.matheus.fooddeliveryapi.api.model.state.StateDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

    private Long id;
    private String name;
    private StateDTO state;
}
