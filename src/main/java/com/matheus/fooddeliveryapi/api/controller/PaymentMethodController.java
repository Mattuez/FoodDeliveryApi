package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.paymentMethod.PaymentMethodDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.paymentMethod.PaymentMethodDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodInputDto;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.PaymentMethod;
import com.matheus.fooddeliveryapi.domain.service.PaymentMethodRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("paymentMethods")
public class PaymentMethodController {

    private PaymentMethodRegistrationService paymentMethodRegistrationService;
    private PaymentMethodDtoAssembler paymentMethodDtoAssembler;
    private PaymentMethodDtoDisassembler paymentMethodDtoDisassembler;

    public PaymentMethodController(PaymentMethodRegistrationService paymentMethodRegistrationService,
                                   PaymentMethodDtoAssembler paymentMethodDtoAssembler,
                                   PaymentMethodDtoDisassembler paymentMethodDtoDisassembler) {
        this.paymentMethodRegistrationService = paymentMethodRegistrationService;
        this.paymentMethodDtoAssembler = paymentMethodDtoAssembler;
        this.paymentMethodDtoDisassembler = paymentMethodDtoDisassembler;
    }

    @CheckSecurity.PaymentMethod.canView
    @GetMapping
    public List<PaymentMethodDto> getAll() {
        return paymentMethodDtoAssembler.toCollectionDto(paymentMethodRegistrationService.searchAll());
    }

    @CheckSecurity.PaymentMethod.canView
    @GetMapping("/{paymentMethodId}")
    public PaymentMethodDto getById(@PathVariable("paymentMethodId") Long paymentMethodId) {
        return paymentMethodDtoAssembler.toDto(paymentMethodRegistrationService.search(paymentMethodId));
    }

    @CheckSecurity.PaymentMethod.canManage
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodDto add(@RequestBody @Valid PaymentMethodInputDto paymentMethodInputDto) {
        PaymentMethod paymentMethod = paymentMethodDtoDisassembler.toEntityObject(paymentMethodInputDto);

        return paymentMethodDtoAssembler.toDto(paymentMethodRegistrationService.insert(paymentMethod));
    }

    @CheckSecurity.PaymentMethod.canManage
    @PutMapping("/{paymentMethodId}")
    public PaymentMethodDto update(@PathVariable("paymentMethodId") Long paymentMethodId,
                                   @RequestBody @Valid PaymentMethodInputDto paymentMethodInputDto) {

        PaymentMethod paymentMethod = paymentMethodRegistrationService.search(paymentMethodId);

        paymentMethodDtoDisassembler.copyToEntityObject(paymentMethodInputDto, paymentMethod);

        return paymentMethodDtoAssembler.toDto(paymentMethodRegistrationService.insert(paymentMethod));
    }

    @CheckSecurity.PaymentMethod.canManage
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("paymentMethodId") Long paymentMethodId) {
        paymentMethodRegistrationService.exclude(paymentMethodId);
    }
}
