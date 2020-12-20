package com.example.paymentinfo.service.serviceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.repository.ClientRepository;
import com.example.paymentinfo.service.PaymentRequestService;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

	private ClientRepository clientRepository;
	private RestTemplate restTemplate;
	
	@Override
	public PaymentRequestDTO getPaymentRequest(Long merchantOrderId) {
		
		
		PaymentRequestDTO pReqDTO = new PaymentRequestDTO();
		
		/*ResponseEntity<PaymentRequestDTO> pReqDTO = restTemplate.postForEntity("https://localhost:844/payment/create-response", bankRequest,
                BankAcquirerResponseDTO.class);
		*/
		return pReqDTO;
		
	}

	@Override
	public PaymentRequestDTO getPaymentUrl(PaymentRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
