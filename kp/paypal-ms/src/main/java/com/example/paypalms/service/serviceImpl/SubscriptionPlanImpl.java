package com.example.paypalms.service.serviceImpl;


import com.example.paypalms.domain.SubscriptionPlan;
import com.example.paypalms.repository.SubscriptionPlanRepository;
import com.example.paypalms.service.SubscriptionPlanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@Log4j2
public class SubscriptionPlanImpl implements SubscriptionPlanService {

    private SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public Collection<SubscriptionPlan> findAll() {
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan entity) {
        return subscriptionPlanRepository.save(entity);
    }

    @Override
    public SubscriptionPlan findById(long id) {
        return subscriptionPlanRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Entity SubscriptionPlan with id {0} does not exist.", id)));
    }

    @Override
    public void delete(SubscriptionPlan entity) {
        subscriptionPlanRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionPlanRepository.deleteById(id);
    }

    @Override
    public SubscriptionPlan update(SubscriptionPlan entity) {
        try{
            findById(entity.getId());
            return subscriptionPlanRepository.save(entity);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw exception;
        }
    }


    @Override
    public Collection<SubscriptionPlan> findAllBySellerId(Long sellerId) {
        return subscriptionPlanRepository.findAllBySellerId(sellerId);
    }

    @Override
    public Collection<SubscriptionPlan> findAllBySellerEmail(String email) {
        return subscriptionPlanRepository.findAllBySellerEmail(email);
    }
}
