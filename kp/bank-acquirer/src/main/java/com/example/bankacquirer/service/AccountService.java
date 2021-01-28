package com.example.bankacquirer.service;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Client;

public interface AccountService {
	
	Account createNewAccount(Client client, String myBankId);

	Account save(Account account);
}
