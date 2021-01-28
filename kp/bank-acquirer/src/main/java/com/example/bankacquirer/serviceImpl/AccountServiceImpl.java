package com.example.bankacquirer.serviceImpl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Bank;
import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.domain.Currency;
import com.example.bankacquirer.repository.AccountRepository;
import com.example.bankacquirer.repository.BankRepository;
import com.example.bankacquirer.repository.CurrencyRepository;
import com.example.bankacquirer.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	private BankRepository bankRepository;
	private CurrencyRepository currencyRepository;
	private AccountRepository accountRepository;

	public AccountServiceImpl(BankRepository bankRepository, CurrencyRepository currencyRepository, AccountRepository accountRepository) {
		this.bankRepository = bankRepository;
		this.currencyRepository = currencyRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Account createNewAccount(Client client,String myBankId) {
		Bank bank = bankRepository.findOneByUniqueBankNumber(myBankId);
	    Currency currency = currencyRepository.findOneByCode("USD");
	    Account newAccount = new Account();
	    String newAccountNumber = RandomStringUtils.randomNumeric(9);
	    newAccount.setAccountNumber(newAccountNumber);
	    newAccount.setAvailableFunds(0);
	    newAccount.setBank(bank);
	    newAccount.setCurrency(currency);
	    newAccount.setOwner(client);
	    
	    return newAccount;
	}

	@Override
	public Account save(Account account){
		return accountRepository.save(account);
	}
    
    
}
