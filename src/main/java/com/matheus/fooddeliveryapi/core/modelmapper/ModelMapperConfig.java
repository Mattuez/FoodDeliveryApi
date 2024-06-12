package com.matheus.fooddeliveryapi.core.modelmapper;

import com.matheus.fooddeliveryapi.api.model.city.CityResumeDto;
import com.matheus.fooddeliveryapi.api.model.orderedProduct.OrderedProductInputDto;
import com.matheus.fooddeliveryapi.domain.model.City;
import com.matheus.fooddeliveryapi.domain.model.OrderedProduct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configMappings(modelMapper);

        return modelMapper;
    }

    private void configMappings(ModelMapper modelMapper) {
        mappingCityResumeDto(modelMapper);
        mappingOrderInputDto(modelMapper);
    }

    private static void mappingCityResumeDto(ModelMapper modelMapper) {
        TypeMap<City, CityResumeDto> cityCityResumeDtoTypeMap =
                modelMapper.createTypeMap(City.class, CityResumeDto.class);

        cityCityResumeDtoTypeMap.addMapping(src -> src.getState().getName(), CityResumeDto::setState);
    }

    private void mappingOrderInputDto(ModelMapper modelMapper) {
        TypeMap<OrderedProductInputDto, OrderedProduct> orderedProductInputDtoOrderedProductTypeMap =
                modelMapper.createTypeMap(OrderedProductInputDto.class, OrderedProduct.class);

        orderedProductInputDtoOrderedProductTypeMap.addMappings(mapper -> {
            mapper.skip(OrderedProduct::setId);
        });
    }
}
