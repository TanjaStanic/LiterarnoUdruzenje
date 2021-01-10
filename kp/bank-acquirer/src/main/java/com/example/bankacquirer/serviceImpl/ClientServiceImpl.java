package com.example.bankacquirer.serviceImpl;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.dto.NewClientDto;
import com.example.bankacquirer.repository.ClientRepository;
import com.example.bankacquirer.service.AccountService;
import com.example.bankacquirer.service.CardService;
import com.example.bankacquirer.service.ClientService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

	@Value("${bankId}")
    private String myBankId;
	
    private ClientRepository clientRepository;
    private AccountService accountService;
    private CardService cardService;

	public ClientServiceImpl(ClientRepository clientRepository, AccountService accountService,
			CardService cardService) {
		super();
		this.clientRepository = clientRepository;
		this.accountService = accountService;
		this.cardService = cardService;
	}

	@Override
    public Client insert(NewClientDto newClient) {
        Client client = clientRepository.findByEmail(newClient.getEmail());
        if (client != null) {
            throw new RuntimeException("Client already exists");
        }
        client = new Client();
        client.setMerchantID(RandomStringUtils.randomAlphanumeric(30));
        client.setMerchantPassword(RandomStringUtils.randomAlphanumeric(100));
        client.setActive(true);
        client.setName(newClient.getName());
        client.setEmail(newClient.getEmail());
        client = clientRepository.save(client);
        
        
        //Create account
        Account newAccount = accountService.createNewAccount(client, myBankId);
        
        //Create card and save both
        cardService.createNewCard(newAccount, myBankId);
        
        return client;
    }

	@Override
	public Client saveClient(Client client) {
		client.setMerchantID(hashData(client.getMerchantID()));
		client.setMerchantPassword(hashData(client.getMerchantPassword()));
		client = clientRepository.save(client);
		return client;
	}

	public static String hashData(String data) {
		String hash = org.springframework.security.crypto.bcrypt.BCrypt.gensalt();
		String hashedData = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(data, hash);
		
		return hashedData;
	}

}
