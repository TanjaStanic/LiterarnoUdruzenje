package com.example.bankacquirer.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;
import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.domain.Transaction;
import com.example.bankacquirer.domain.TransactionStatus;
import com.example.bankacquirer.dto.PccRequestDTO;
import com.example.bankacquirer.dto.PccResponseDTO;
import com.example.bankacquirer.repository.AccountRepository;
import com.example.bankacquirer.repository.CardRepository;
import com.example.bankacquirer.repository.ClientRepository;
import com.example.bankacquirer.repository.CurrencyRepository;
import com.example.bankacquirer.repository.TransactionRepository;
import com.example.bankacquirer.service.PccService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PccServiceImpl implements PccService{

	@Value("${bankId}")
    private String myBankId;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private ClientRepository clientRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CurrencyRepository currencyRepository;


	@Override
	public PccResponseDTO createResponse(PccRequestDTO pccRequestDto) {
		Transaction transaction = new Transaction();
        long generatedId = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

        transaction.setId(generatedId);
        transaction.setAcquirerOrderId(pccRequestDto.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(pccRequestDto.getAcquirerTimestamp());
        transaction.setAmount(pccRequestDto.getAmount());
        transaction.setStatus(TransactionStatus.CREATED);
        transaction.setCurrency(currencyRepository.findOneByCode(pccRequestDto.getCurrencyCode()));
        transaction = transactionRepository.save(transaction);
        log.error("INFO | Transaction created");
        
		PccResponseDTO pccResponseDto = new PccResponseDTO();
		pccResponseDto.setAcquirerOrederId(pccRequestDto.getAcquirerOrderId());
		pccResponseDto.setAcquirerTimestamp(pccRequestDto.getAcquirerTimestamp());	
		pccResponseDto.setIssuerTimestamp(new Date());
		pccResponseDto.setIssuerOrderId(transaction.getId());
		
		//CHECK AUTHENTIFICATION
		List<Card> cards = cardRepository.findAll();
        Card card = new Card();
        boolean cardFound = false;
        for (Card c : cards) {
            if (org.springframework.security.crypto.bcrypt.BCrypt.checkpw(pccRequestDto.getPanNumber(), c.getPan())) {
                card = c;
                cardFound = true;
            }
        }
        if (!cardFound) {
        	System.out.println("Card with that pan number doesn't exist.");
        	pccResponseDto.setIsAuthentificated(false);
    		pccResponseDto.setIsAutorized(false);
    		
    		transaction.setStatus(TransactionStatus.ERROR);
    		transactionRepository.save(transaction);   		
    		log.error("ERROR | Transaction canceled | There is no such card in the bank.");
    		
    		return pccResponseDto;
        }
        //check if another data on card are good
        String tempExpDate = pccRequestDto.getMm() + "/" + pccRequestDto.getYy();

         Account account = accountRepository.findOneById(card.getAccount().getId());
         Client client = clientRepository.findOneByAccount(account);
        
         if (client != null || account != null) {
             if (!(org.springframework.security.crypto.bcrypt.BCrypt.checkpw(pccRequestDto.getCvv(), card.getCvv()))
                     || !card.getExpirationDate().equals(tempExpDate) ||
                     !(client.getName().equals(pccRequestDto.getCardHolder()))) {

                System.out.println("Other data elements on card are not the same.");
                pccResponseDto.setIsAuthentificated(false);
                pccResponseDto.setIsAutorized(false);
         		
                transaction.setStatus(TransactionStatus.ERROR);
        		transactionRepository.save(transaction);
        		log.info("ERROR | Transaction canceled | Card data doesn't match");
        		
         		return pccResponseDto;
             }
         }

         transaction.setClient(client);
         //CHECK IF TRANSACTION IS AUTORIZED - PAYMENT FAILED
         if (pccRequestDto.getAmount() > account.getAvailableFunds()) {
        	 pccResponseDto.setIsAuthentificated(true);
        	 pccResponseDto.setIsAutorized(false);
        	
        	transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
     		transactionRepository.save(transaction);
        	 
     		log.info("CANCELED | Transaction canceled | No available funds: ");
        	 return pccResponseDto;
         }
         
		
        //DO PAYMENT
        account.setAvailableFunds(account.getAvailableFunds() - pccRequestDto.getAmount());
        accountRepository.save(account);
        
        
        //save transaction
        
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(transaction);
        log.info("COMPLETED | Bank Payment | Transaction successful");
        
        pccResponseDto.setIsAuthentificated(true);
        pccResponseDto.setIsAutorized(true);
		
        return pccResponseDto;
	}
	
}
