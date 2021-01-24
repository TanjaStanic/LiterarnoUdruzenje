package com.example.paymentinfo.dto;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String taxIdentificationNumber;
    private String companyRegistrationNumber;
    private String email;
    private String name;
    private List<PaymentMethodDto> paymentMethods;

    public ClientDto(Client client){
        this.id = client.getId();
        this.taxIdentificationNumber = client.getTaxIdentificationNumber();
        this.companyRegistrationNumber = client.getCompanyRegistrationNumber();
        this.email = client.getEmail();
        this.name = client.getName();
        this.paymentMethods = client.getPaymentMethods().stream().map(paymentMethod -> new PaymentMethodDto(paymentMethod)).collect(Collectors.toList());
    }
}
