package com.example.bankacquirer.service;

import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.dto.NewClientDto;

public interface ClientService {

    Client insert(NewClientDto newClient);
    Client saveClient(Client client);
}
