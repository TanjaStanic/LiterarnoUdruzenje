package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.SubscriptionType;
import com.example.paypalms.repository.SubscriptionTypeRepository;
import com.example.paypalms.service.SubscriptionTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@Log4j2
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public Collection<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType save(SubscriptionType entity) {
        return subscriptionTypeRepository.save(entity);
    }

    @Override
    public SubscriptionType findById(long id) {
        return subscriptionTypeRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Entity SubscriptionType with id {0} does not exist.", id)));
    }

    @Override
    public void delete(SubscriptionType entity) {
        subscriptionTypeRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionTypeRepository.deleteById(id);
    }

    @Override
    public SubscriptionType update(SubscriptionType entity) {
        try{
            findById(entity.getId());
            return subscriptionTypeRepository.save(entity);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw exception;
        }
    }
}
