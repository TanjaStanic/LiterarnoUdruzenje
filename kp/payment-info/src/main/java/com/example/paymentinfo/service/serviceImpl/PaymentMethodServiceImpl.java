package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.PaymentMethodService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Collection<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod save(PaymentMethod entity) {
        return paymentMethodRepository.save(entity);
    }

    @Override
    public PaymentMethod findById(long id) {
        return null;
    }

    @Override
    public void delete(PaymentMethod entity) {
        paymentMethodRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        paymentMethodRepository.deleteById(id);
    }

    @Override
    public PaymentMethod update(PaymentMethod entity) {
        return null;
    }

    @Override
    public Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email) {
        return paymentMethodRepository.findAllBySubscriptionSupportedIsTrueAndClientsEmail(email);
    }
}
