package com.example.bankacquirer.serviceImpl;

import com.example.bankacquirer.dto.CardDataDTO;
import com.example.bankacquirer.dto.CompletedPaymentDTO;
import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;
import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;
import com.example.bankacquirer.repository.AccountRepository;
import com.example.bankacquirer.repository.CardRepository;
import com.example.bankacquirer.repository.ClientRepository;
import com.example.bankacquirer.repository.PcRequestRepository;
import com.example.bankacquirer.repository.TransactionRepository;
import com.example.bankacquirer.service.PaymentService;

import lombok.extern.log4j.Log4j2;

import java.time.ZonedDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;
import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.domain.PaymentConcentratorRequest;
import com.example.bankacquirer.domain.Transaction;
import com.example.bankacquirer.domain.TransactionStatus;


@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

	private PcRequestRepository pcRequestRepository;
	private CardRepository cardRepository;
	private ClientRepository clientRepository;
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;
	private RestTemplate restTemplate;

	@Autowired
	public PaymentServiceImpl(PcRequestRepository pcRequestRepository, CardRepository cardRepository,
							  ClientRepository clientRepository, AccountRepository accountRepository,
							  TransactionRepository transactionRepository, RestTemplate restTemplate) {
		this.pcRequestRepository = pcRequestRepository;
		this.cardRepository = cardRepository;
		this.clientRepository = clientRepository;
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.restTemplate = restTemplate;
	}

	private String myBankId = "123412";
	

	@Override
	public boolean checkRequest(PaymentConcentratorRequestDTO pcRequestDTO) {
		if (pcRequestDTO.getMerchantId()==null || pcRequestDTO.getMerchantPassword()==null ||
				pcRequestDTO.getMerchantTimestamp()==null || pcRequestDTO.getSuccessUrl()==null ||
				pcRequestDTO.getFailedUrl()==null || pcRequestDTO.getErrorUrl()==null) {
			log.info("ERROR | Payment Concentrator Requests | Some of parameters are missing");
			System.out.println("Some of requests parameters are missing");
			return false;
		}
		if (pcRequestDTO.getAmount()<=0) {
			log.info("ERROR | Payment Concentrator Requests | Amount can not be null");
			System.out.println("Amount can not be negative or zero");
			return false;
		}
		
		Client merchant = clientRepository.findOneByMerchantID(pcRequestDTO.getMerchantId());
		if (merchant==null || merchant.getAccount()==null) {
			log.info("ERROR | Payment Concentrator Requests | Merchant acc i doesnt exists in this bank or doesn't have account in this bank");
			System.out.println("Merchant acc i doesnt exists in this bank or doesn't have account in this bank!");		
			return false;
		}
		if ( !merchant.getMerchantPassword().equals(pcRequestDTO.getMerchantPassword())) {
			log.info("ERROR | Payment Concentrator Requests | Merchant password is not ok!");
			System.out.println("Merchant password is not ok!");	
			return false;
		}
		return true;
	}
	
	@Override
	public PaymentConcentratorResponseDTO createResponse(PaymentConcentratorRequestDTO pcRequestDTO) {
		
		PaymentConcentratorRequest pcReq = new PaymentConcentratorRequest(pcRequestDTO.getAmount(),
				pcRequestDTO.getMerchantId(), pcRequestDTO.getMerchantPassword(), pcRequestDTO.getMerchantOrderId(),
				pcRequestDTO.getMerchantTimestamp(), pcRequestDTO.getSuccessUrl(),
				pcRequestDTO.getFailedUrl(), pcRequestDTO.getErrorUrl());
		
		pcReq = pcRequestRepository.save(pcReq);
		log.info("CREATED | Payment Concentrator Requests | Payment Id: " + pcReq.getId());
		return new PaymentConcentratorResponseDTO(pcReq.getId(),"https://localhost:8445/card-data/"+pcReq.getId());
	}

	@Override
	public String confirmPayment(CardDataDTO cardDataDTO, Long pcRequestId) {
		
		//CHECK IF ALL THE ELEMENTS ARE OK
				
		PaymentConcentratorRequest pcRequest = pcRequestRepository.getOne(pcRequestId);
		Transaction transaction = new Transaction();
		
		transaction.setAmount(pcRequest.getAmount());
		transaction.setMerchantOrderId(pcRequest.getMerchantOrderId());
		transaction.setAcquirerTimestamp(pcRequest.getMerchantTimestamp());
		
		//check if bank acquirer and bank issuer are the same
		//first 6 digits must be the same
		String temp = cardDataDTO.getPanNumber().replace("-", "");
		String number = temp.substring(0, 6);
	
		// IF bank acquirer and bank issuer are the same
		if (myBankId.contentEquals(number)) {
			System.out.println("Bank-acq and bank Issuer are the same");
			
			
			List<Card> cards = cardRepository.findAll();
			Card card = new Card();
			boolean cardFound = false;
			for (Card c : cards) {
				if (org.springframework.security.crypto.bcrypt.BCrypt.checkpw(cardDataDTO.getPanNumber(), c.getPan())) {
					System.out.println("Bank has card with this number : " + c.getPan());
					card = c;
					cardFound = true;
				}
			}
			if (!cardFound) {
				System.out.println("Bank-acq dosn't have card with thih pan number : " + cardDataDTO.getPanNumber());
				log.info("CANCELED | Transaction canceled | There is no such card in the bank");
				transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
				paymentFailed(pcRequest);
				return pcRequest.getFailedUrl();
			}
			
			
			//check if another data on card are good
			String tempExpDate = cardDataDTO.getMm() + "/" + cardDataDTO.getYy();

			Account account = accountRepository.findOneById(card.getAccount().getId());
			Client client = clientRepository.findOneByAccount(account);
			if (client!=null || account!=null) {
				if (!(org.springframework.security.crypto.bcrypt.BCrypt.checkpw(cardDataDTO.getCvv(), card.getCvv())) 
						|| !card.getExpirationDate().equals(tempExpDate) ||
						!(client.getName().equals(cardDataDTO.getCardHolder()))) {
						
					System.out.println("Other data elements on card are not the same.");
					transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
					paymentFailed(pcRequest);
					log.info("CANCELED | Transaction canceled | Card data doesn't match");
					return pcRequest.getFailedUrl();
				}
			}
			
			// check avalailable funds
			if (pcRequest.getAmount()>account.getAvailableFunds()) {
				
				System.out.println("No available funds!");				
				transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
				paymentFailed(pcRequest);
				log.info("CANCELED | Transaction canceled | No available funds: "+ account.getAvailableFunds());
				return pcRequest.getFailedUrl();
			}
			
			//check data about merchant
			Client merchant = clientRepository.findOneByMerchantID(pcRequest.getMerchantId());
			if (merchant==null || !merchant.getMerchantPassword().equals(pcRequest.getMerchantPassword())) {
				
				System.out.println("Merchant Data is not good!");
				transaction.setStatus(TransactionStatus.ERROR);
				paymentFailed(pcRequest);
				log.error("ERROR | Transaction error | Marchant does'n match");
				return pcRequest.getErrorUrl();
			}
			
			System.out.println("all good");
			//everything is fine, do payment
			Account merchantAccount = accountRepository.findOneByOwner(merchant);
			account.setAvailableFunds(account.getAvailableFunds() - pcRequest.getAmount());
			merchantAccount.setAvailableFunds(merchantAccount.getAvailableFunds() + pcRequest.getAmount());
			
			//save all changes
			accountRepository.save(merchantAccount);
			accountRepository.save(account);
			
			clientRepository.save(client);
			clientRepository.save(merchant);			
			
			transaction.setClient(client);
			transaction.setStatus(TransactionStatus.SUCCESSFUL);
			Transaction saved = transactionRepository.save(transaction);
			
			log.info("COMPLETED | Bank Payment | Transaction successful");
			
			CompletedPaymentDTO cpDTO = new CompletedPaymentDTO();
			cpDTO.setTransactionStatus(TransactionStatus.SUCCESSFUL);
			cpDTO.setMerchantOrderID(pcRequest.getMerchantOrderId());
			//cpDTO.setAcquirerOrderID(saved.getId());
			cpDTO.setAcquirerTimestamp(ZonedDateTime.now());
			cpDTO.setPaymentID(pcRequest.getId());
			
			//otkomentarisati kada se dovrsi metoda u bank ms
			
			try {	
				ResponseEntity<String> response = restTemplate.exchange("https://localhost:8441/api/complete-payment", HttpMethod.POST,
						new HttpEntity<CompletedPaymentDTO>(cpDTO), String.class);
			} catch (Exception e) {
				log.error("Could not contact complete-payment in bankMS");
				log.error(e.getMessage());
		    }

			return pcRequest.getSuccessUrl();
			
		}
		
		
		// else if bank acq and bank issuer are NOT the same, send request to PCC
		else {
			System.out.println("Bank-acq and bank Issuer are NOT the same");
			//TO DO
		}

		
		return pcRequest.getErrorUrl();
	}
	
	public void paymentFailed(PaymentConcentratorRequest pcRequest) {
		CompletedPaymentDTO cpDTO = new CompletedPaymentDTO();
		cpDTO.setTransactionStatus(TransactionStatus.UNSUCCESSFUL);
		cpDTO.setMerchantOrderID(pcRequest.getMerchantOrderId());
		cpDTO.setPaymentID(pcRequest.getId());
		
		//otkomentarisati kada se dovrsi metoda u bank ms
		
		try {
			ResponseEntity<String> response = restTemplate.exchange("https://localhost:8441/api/complete-payment", HttpMethod.POST,
					new HttpEntity<CompletedPaymentDTO>(cpDTO), String.class);
		} catch (Exception e) {
			log.error(e.getMessage());
	    }
	}

}
