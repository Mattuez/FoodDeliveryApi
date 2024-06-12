package com.matheus.fooddeliveryapi.api.model.address;

import com.matheus.fooddeliveryapi.api.model.city.CityResumeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private String postalCode;
    private String name;
    private String number;
    private String complement;
    private String district;
    private CityResumeDto city;
}
