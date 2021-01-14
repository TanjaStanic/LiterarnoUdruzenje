package com.example.bankacquirer.service;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;

public interface CardService {
	
	Card createNewCard(Account account, String myBankId);
}
