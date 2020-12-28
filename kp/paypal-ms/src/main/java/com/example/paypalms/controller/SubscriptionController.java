package com.example.paypalms.controller;

import com.example.paypalms.domain.SubscriptionPlan;
import com.example.paypalms.dto.SubscriptionPlanDto;
import com.example.paypalms.dto.SubscriptionRequestDto;
import com.example.paypalms.service.SubscriptionPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private SubscriptionPlanService subscriptionPlanService;

    public SubscriptionController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @PostMapping
    public ResponseEntity<?> createBillingPlan(@RequestBody SubscriptionRequestDto subscriptionRequest) {

        return ResponseEntity.ok("https://www.facebook.com");
    }

    @GetMapping("/{sellerEmail}")
    public ResponseEntity<SubscriptionPlanDto[]> getSubscriptionPlansForSeller(@PathVariable String sellerEmail) {
        List<SubscriptionPlan> subscriptionPlans = (ArrayList) subscriptionPlanService.findAllBySellerEmail(sellerEmail);
        List<SubscriptionPlanDto> plans = subscriptionPlans.stream().map(SubscriptionPlanDto::new).collect(Collectors.toList());

        SubscriptionPlanDto[] retVal = plans.toArray(new SubscriptionPlanDto[0]);

        return ResponseEntity.ok(retVal);
    }
}
