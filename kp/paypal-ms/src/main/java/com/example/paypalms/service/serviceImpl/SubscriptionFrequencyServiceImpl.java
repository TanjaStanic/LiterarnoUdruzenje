package com.example.paypalms.service.serviceImpl;


import com.example.paypalms.domain.SubscriptionFrequency;
import com.example.paypalms.repository.SubscriptionFrequencyRepository;
import com.example.paypalms.service.SubscriptionFrequencyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@Log4j2
public class SubscriptionFrequencyServiceImpl implements SubscriptionFrequencyService {

    private SubscriptionFrequencyRepository subscriptionFrequencyRepository;

    public SubscriptionFrequencyServiceImpl(SubscriptionFrequencyRepository subscriptionFrequencyRepository) {
        this.subscriptionFrequencyRepository = subscriptionFrequencyRepository;
    }

    @Override
    public Collection<SubscriptionFrequency> findAll() {
        return subscriptionFrequencyRepository.findAll();
    }

    @Override
    public SubscriptionFrequency save(SubscriptionFrequency entity) {
        return subscriptionFrequencyRepository.save(entity);
    }

    @Override
    public SubscriptionFrequency findById(long id) {
        return subscriptionFrequencyRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Entity SubscriptionFrequency with id {0} does not exist.", id)));
    }

    @Override
    public void delete(SubscriptionFrequency entity) {
        subscriptionFrequencyRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionFrequencyRepository.deleteById(id);
    }

    @Override
    public SubscriptionFrequency update(SubscriptionFrequency entity) {
        try{
            findById(entity.getId());
            return subscriptionFrequencyRepository.save(entity);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw exception;
        }
    }
}
