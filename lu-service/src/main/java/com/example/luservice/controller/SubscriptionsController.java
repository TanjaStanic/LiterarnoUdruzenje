package com.example.luservice.controller;

import com.example.luservice.dto.SubscriptionDto;
import com.example.luservice.model.SubscriptionStatus;
import com.example.luservice.model.UserSubscription;
import com.example.luservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth/subscriptions")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class SubscriptionsController {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserSubscription(@PathVariable long id){
        UserSubscription subscription =
                subscriptionRepository.findByOwnerIdAndSubscriptionStatusIn(id, Arrays.asList(SubscriptionStatus.ACTIVE, SubscriptionStatus.INITIATED));

        if (subscription != null){
            SubscriptionDto subscriptionDto = new SubscriptionDto(subscription.getId(), subscription.getExpirationDate(),
                    subscription.getCurrency(),subscription.getPaymentAmount(), subscription.getMerchantOrderId(), subscription.getSubscriptionStatus().toString());
            return ResponseEntity.ok(subscriptionDto);
        }

        return ResponseEntity.badRequest().build();
    }
}
