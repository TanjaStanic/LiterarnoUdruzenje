package com.example.paymentinfo.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentMethod {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column
    private String name;
    
    @Column
    private boolean subscriptionSupported;

    @Column
    private String applicationName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "paymentMethods")
    private Set<Client> clients = new HashSet<>();

    public PaymentMethod(String name, boolean subscriptionSupported, String applicationName) {
        this.name = name;
        this.subscriptionSupported = subscriptionSupported;
        this.applicationName = applicationName;
        this.clients = new HashSet<>();
    }

    public void addClient(Client client) {
        clients.add(client);
        client.getPaymentMethods().add(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.getPaymentMethods().remove(this);
    }

}
