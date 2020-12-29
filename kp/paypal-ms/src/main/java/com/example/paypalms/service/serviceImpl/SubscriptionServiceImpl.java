package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Subscription;
import com.example.paypalms.repository.SubscriptionRepository;
import com.example.paypalms.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Collection<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription save(Subscription entity) {
        return subscriptionRepository.save(entity);
    }

    @Override
    public Subscription findById(long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Subscription with id {0} does not exist.", id)));
    }

    @Override
    public void delete(Subscription entity) {
        subscriptionRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription update(Subscription entity) {
        return null;
    }
}
