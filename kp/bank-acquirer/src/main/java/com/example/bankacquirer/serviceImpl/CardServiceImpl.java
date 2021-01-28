package com.example.bankacquirer.serviceImpl;

import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;
import com.example.bankacquirer.repository.AccountRepository;
import com.example.bankacquirer.repository.CardRepository;
import com.example.bankacquirer.service.CardService;

@Service
public class CardServiceImpl implements CardService{

	private CardRepository cardRepository;
	private AccountRepository accountRepository;

	public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository) {
		this.cardRepository = cardRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Card createNewCard(Account account, String myBankId) {
		
		Card newCard = new Card();
        Calendar c = Calendar.getInstance();

        Integer year=c.get(Calendar.YEAR) + 2;
        Integer month=c.get(Calendar.MONTH)+ 1;
        String monthStr = month.toString();
        if (month<10) {
        	monthStr = "0" + month.toString();
        }
       
        String expDate = monthStr + "/" + year.toString().substring(2,4);
        String pan = myBankId + account.getAccountNumber() + RandomStringUtils.randomNumeric(1);
        pan = formatCard(pan);
        
        newCard.setAccount(account);
        newCard.setExpirationDate(expDate);
        newCard.setCvv(hashData(RandomStringUtils.randomNumeric(3)));
        newCard.setPan(hashData(pan));
       
        newCard = cardRepository.save(newCard);
		
        account.setAccountNumber(hashData(account.getAccountNumber()));
        account.getCards().add(newCard);

        account = accountRepository.save(account);

        return newCard;
	}
	
	public static String formatCard(String cardNumber) {
	    if (cardNumber == null) return null;
	    char delimiter = '-';
	    return cardNumber.replaceAll(".{4}(?!$)", "$0" + delimiter);
	}
	
	public static String hashData(String data) {
		String hash = org.springframework.security.crypto.bcrypt.BCrypt.gensalt();
		String hashedData = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(data, hash);
		
		return hashedData;
	}
}
