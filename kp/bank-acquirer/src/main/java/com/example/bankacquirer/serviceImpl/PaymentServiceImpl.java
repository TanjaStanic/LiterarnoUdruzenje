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

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bankacquirer.domain.Account;
import com.example.bankacquirer.domain.Card;
import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.domain.PaymentConcentratorRequest;
import com.example.bankacquirer.domain.Transaction;
import com.example.bankacquirer.domain.TransactionStatus;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PcRequestRepository pcRequestRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	
	private RestTemplate restTemplate;
	
	private String myBankId = "123412";
	
	@Override
	public PaymentConcentratorResponseDTO createResponse(PaymentConcentratorRequestDTO pcRequestDTO) {
		
		PaymentConcentratorRequest pcReq = new PaymentConcentratorRequest(pcRequestDTO.getAmount(),
				pcRequestDTO.getMerchantId(), pcRequestDTO.getMerchantPassword(), pcRequestDTO.getMerchantOrderId(),
				pcRequestDTO.getMerchantTimestamp(), pcRequestDTO.getSuccessUrl(),
				pcRequestDTO.getFailedUrl(), pcRequestDTO.getErrorUrl());
		
		pcRequestRepository.save(pcReq);

		return new PaymentConcentratorResponseDTO(pcReq.getId(),"https://localhost:8445/card-data");
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
						
			Card card = cardRepository.findOneByPan(cardDataDTO.getPanNumber());
			if (card==null) {
				System.out.println("Bank-acq dosn't have card with thih pan number : " + cardDataDTO.getPanNumber());
				transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
				paymentFailed(pcRequest);
				return pcRequest.getFailedUrl();
			}
			
			//check if another data on card are good
			String tempExpDate = cardDataDTO.getMm() + "/" + cardDataDTO.getYy();
			
			Account account = accountRepository.findOneById(card.getAccount().getId());
			Client client = clientRepository.findOneByAccount(account);
			if (client!=null || account!=null) {
				if (!card.getCvv().equals(cardDataDTO.getCvv()) || !card.getExpirationDate().equals(tempExpDate) ||
						!(client.getName().equals(cardDataDTO.getCardHolder()))) {
						
					System.out.println("Other data elements on card are not the same.");
					transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
					paymentFailed(pcRequest);
					return pcRequest.getFailedUrl();
				}
			}
			
			// check avalailable funds
			if (pcRequest.getAmount()>account.getAvailableFunds()) {
				
				System.out.println("No available funds!");				
				transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
				paymentFailed(pcRequest);
				return pcRequest.getFailedUrl();
			}
			
			//check data about merchant
			Client merchant = clientRepository.findOneByMerchantID(pcRequest.getMerchantId());
			if (merchant==null || !merchant.getMerchantPassword().equals(pcRequest.getMerchantPassword())) {
				
				System.out.println("Merchant Data is not good! Ne podudaraju se");
				transaction.setStatus(TransactionStatus.ERROR);
				paymentFailed(pcRequest);
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
			
			CompletedPaymentDTO cpDTO = new CompletedPaymentDTO();
			cpDTO.setTransactionStatus(TransactionStatus.SUCCESSFUL);
			cpDTO.setMerchantOrderID(pcRequest.getMerchantOrderId());
			cpDTO.setAcquirerOrderID(saved.getId());
			cpDTO.setAcquirerTimestamp(ZonedDateTime.now());
			
			
			//otkomentarisati kada se dovrsi metoda u bank ms
			
			/*try {	
				ResponseEntity<String> response = restTemplate.exchange("https://banking/api/complite-payment", HttpMethod.POST, 
						new HttpEntity<CompletedPaymentDTO>(cpDTO), String.class);
			} catch (Exception e) {
				throw new RuntimeException("Coud not contact complite-payment in bankMS");
		    }*/

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

		//otkomentarisati kada se dovrsi metoda u bank ms
		/*
		try {	
			ResponseEntity<String> response = restTemplate.exchange("https://banking/api/complite-payment", HttpMethod.POST, 
					new HttpEntity<CompletedPaymentDTO>(cpDTO), String.class);
		} catch (Exception e) {
			throw new RuntimeException("Coud not contact complite-payment in BANK ms");
	    }*/
	}



}
