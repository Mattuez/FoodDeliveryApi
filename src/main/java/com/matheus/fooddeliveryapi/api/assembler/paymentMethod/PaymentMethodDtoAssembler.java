package com.matheus.fooddeliveryapi.api.assembler.paymentMethod;

import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import com.matheus.fooddeliveryapi.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PaymentMethodDtoAssembler {

    private ModelMapper modelMapper;

    public PaymentMethodDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentMethodDto toDto(PaymentMethod source) {
        return modelMapper.map(source, PaymentMethodDto.class);
    }

    public List<PaymentMethodDto> toCollectionDto(Collection<PaymentMethod> source) {
        return source.stream()
                .map(paymentMethod -> toDto(paymentMethod))
                .toList();
    }
}
