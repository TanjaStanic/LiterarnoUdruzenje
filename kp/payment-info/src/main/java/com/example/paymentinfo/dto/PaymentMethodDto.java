package com.example.paymentinfo.dto;

import com.example.paymentinfo.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {

    private Long id;
    private String name;
    private boolean subscriptionSupported;
    private String applicationName;

    public PaymentMethodDto(PaymentMethod paymentMethod){
        this.id = paymentMethod.getId();
        this.name = paymentMethod.getName();
        this.subscriptionSupported = paymentMethod.isSubscriptionSupported();
        this.applicationName = paymentMethod.getApplicationName();
    }
}
