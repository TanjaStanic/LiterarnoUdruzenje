package upp.la.service;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;

import upp.la.repository.CardRepository;
import upp.la.repository.UserRepository;

@Service
public class SubmitPaymentService implements JavaDelegate {

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	CardRepository cardReposotory;
	
	@Autowired 
	RuntimeService runtimeService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("In submit payment");
			
		
		boolean paymentSuccess = true;
		paymentSuccess = checkPayment();
		if (paymentSuccess) {
			execution.setVariable("payment_success", true);
		}
		else {
			execution.setVariable("payment_success", false);

		}
		runtimeService
		  .createSignalEvent("PaymentSignal") 
		  .send();
}
	
	public boolean checkCardForm(List<FormFieldDto> formFields) {
		for (FormFieldDto f : formFields) {
			if (f.getFieldId().equals("card_holder_id")) {
				if (f.getFieldValue().trim().isEmpty()) {
					System.out.println("card holder je false");
					return false;
				}
			}
			if (f.getFieldId().equals("card_number_id")) {
				if (f.getFieldValue().trim().isEmpty() ) {
					return false;
				}
			}
			if (f.getFieldId().equals("cvv_id")) {
				if (f.getFieldValue().trim().isEmpty()) {
					return false;
				}
			}
			if (f.getFieldId().equals("expiration_date_id")) {
				if (f.getFieldValue().trim().isEmpty() ) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkPayment() {
		System.out.println("In check payment method");
		return true;
	}


}
