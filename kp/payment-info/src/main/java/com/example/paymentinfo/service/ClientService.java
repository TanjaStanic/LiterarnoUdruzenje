package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.Client;

import java.util.Collection;
import java.util.Set;

public interface ClientService {
    Client insert(Client client);

    Client update(Client updated);

    void delete(Client client);

    Client findByEmail(String email);

    Client findById(Long id);

    void deleteById(Long id);

    Collection<Client> getAll();

    Client findByToken(String token);

    Client findByUsername(String username);

    void saveRange(Collection<Client> clients);
    
    Client save(Client client);
    
    Client updatePaymentMethod(String paymentMethodName,String email);

}
