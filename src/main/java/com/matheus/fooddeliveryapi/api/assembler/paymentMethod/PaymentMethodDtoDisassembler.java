package com.matheus.fooddeliveryapi.api.assembler.paymentMethod;

import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodInputDto;
import com.matheus.fooddeliveryapi.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodDtoDisassembler {

    private ModelMapper modelMapper;

    public PaymentMethodDtoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentMethod toEntityObject(PaymentMethodInputDto source) {
        return modelMapper.map(source, PaymentMethod.class);
    }

    public void copyToEntityObject(PaymentMethodInputDto source, PaymentMethod destination) {
        modelMapper.map(source, destination);
    }
}
