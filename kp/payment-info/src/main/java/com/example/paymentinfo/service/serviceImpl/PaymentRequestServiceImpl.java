package com.example.paymentinfo.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.service.PaymentRequestService;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

	@Override
	public PaymentRequestDTO getPaymentRequest(Long merchantOrderId) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public PaymentRequestDTO getPaymentUrl(PaymentRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}