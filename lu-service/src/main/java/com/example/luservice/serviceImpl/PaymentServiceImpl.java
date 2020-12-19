package com.example.luservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.luservice.model.Client;
import com.example.luservice.model.FormData;
import com.example.luservice.model.PaymentData;
import com.example.luservice.model.PaymentMethod;
import com.example.luservice.repository.ClientRepository;
import com.example.luservice.repository.PaymentDataRepository;
import com.example.luservice.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	 @Autowired
	 private ClientRepository sellerRepository;
	 
	 @Autowired
	 private PaymentDataRepository paymentDataRepository;
	
	@Override
    public boolean newSellerPaymentMethods(List<PaymentMethod> paymentMethods, Long userId) {

        Client seller = this.sellerRepository.findClientByUserId(userId);
        if (seller == null) {
            seller = new Client();
            seller.setId(userId);
        }

        if (seller.getPaymentMethods() == null) {
            seller.setPaymentMethods(new ArrayList<>());
        }
        if (seller.getPaymentsData() == null) {
            seller.setPaymentsData(new ArrayList<>());
        }

        for (PaymentMethod paymentMethod : paymentMethods) {
            List<PaymentData> paymentsData = new ArrayList<>();
            boolean valid = true;
            for (FormData formData : paymentMethod.getRequiredFormData()) {
                if (formData.getValue() != null && !formData.getValue().equals("")) {
                    paymentsData.add(new PaymentData(formData.getCode(), formData.getValue()));
                } else {
                    valid = false;
                }
            }
            if (paymentsData.size() != 0 && valid == true) {
                seller.getPaymentMethods().add(paymentMethod);
                seller.getPaymentsData().addAll(paymentsData);      // doda sva polja potrebna za banku npr
            }
        }

        List<PaymentData> paymentData = seller.getPaymentsData();
        seller.setPaymentsData(null);
        this.sellerRepository.save(seller);

        for (int i = 0; i < paymentData.size(); i++) {
            PaymentData pd = new PaymentData();
            pd.setName(paymentData.get(i).getName());
            pd.setValue(paymentData.get(i).getValue());
            paymentData.set(i, this.paymentDataRepository.save(pd));
        }

        seller.setPaymentsData(paymentData);
        this.sellerRepository.save(seller);

        return true;
    }

}
