package com.example.luservice.controller;

import com.example.luservice.dto.SubscriptionDto;
import com.example.luservice.model.SubscriptionStatus;
import com.example.luservice.model.UserSubscription;
import com.example.luservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
                subscriptionRepository.findByOwnerIdAndSubscriptionStatusIn(id, Arrays.asList(SubscriptionStatus.ACTIVE));

        if (subscription != null){
            SubscriptionDto subscriptionDto = new SubscriptionDto(subscription.getExpirationDate(),
                    subscription.getSubscriptionStatus(),
                    subscription.getPaymentAmount(),
                    subscription.getMerchantOrderId(),
                    subscription.getCurrency());
            return ResponseEntity.ok(subscriptionDto);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> saveSubscription(@RequestBody SubscriptionDto subscription){
        UserSubscription userSubscription = subscriptionRepository.findByMerchantOrderId(subscription.getMerchantOrderId());
        userSubscription.setSubscriptionStatus(subscription.getSubscriptionStatus());
        userSubscription.setExpirationDate(subscription.getExpirationDate());
        subscriptionRepository.save(userSubscription);
        return ResponseEntity.ok("http://localhost:4200/dashboard");
    }
}
