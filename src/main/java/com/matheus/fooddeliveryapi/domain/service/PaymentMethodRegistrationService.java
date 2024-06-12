package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.PaymentMethodNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.PaymentMethod;
import com.matheus.fooddeliveryapi.domain.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentMethodRegistrationService {
    public static final String MSG_PAYMENT_METHOD_BEING_USED = "Payment Method with code %d is being used and can't be removed";

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodRegistrationService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> searchAll() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod search(Long paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodNotFoundException(paymentMethodId));
    }

    @Transactional
    public PaymentMethod insert(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public void exclude(Long paymentMethodId) {
        try {
            paymentMethodRepository.deleteById(paymentMethodId);
            paymentMethodRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(paymentMethodId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PAYMENT_METHOD_BEING_USED, paymentMethodId)
            );
        }
    }
}