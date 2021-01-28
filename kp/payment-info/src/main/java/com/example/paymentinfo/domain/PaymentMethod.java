package com.example.paymentinfo.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class PaymentMethod {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter
	private Long id;
	
    @Column
    private String name;
    
    @Column
    @Setter
    private boolean subscriptionSupported;

    @Column
    private String applicationName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "paymentMethods")
    private Set<Client> clients = new HashSet<>();

    public PaymentMethod(String name, boolean subscriptionSupported, String applicationName) {
        Assert.notNull(name, "Name cannot be null");
        Assert.notNull(applicationName, "applicationName cannot be null");
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

    public void setName(String name) {
        Assert.notNull(name, "Name cannot be null");
        this.name = name;
    }

    public void setApplicationName(String applicationName) {
        Assert.notNull(applicationName, "applicationName cannot be null");
        this.applicationName = applicationName;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
