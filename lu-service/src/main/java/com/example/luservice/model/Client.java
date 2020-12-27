package com.example.luservice.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String taxIdentificationNumber; // pib

    @Column
    private String companyRegistrationNumber; // maticni broj

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;		// hesirati

    @Column
    private boolean active;

    @Column
    private boolean enabled;

    @Column
    private Date lastPasswordResetDate;

    @OneToMany
    private List<PaymentData> paymentsData;

    @ManyToMany
    private List<PaymentMethod> paymentMethods;

    @Column
    private Long userId;

}
