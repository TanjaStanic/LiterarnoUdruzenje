package upp.la.service;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.CatchEvent;
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
		//CatchEvent signal =  (CatchEvent)execution.getBpmnModelElementInstance();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("payment_success", true);
		try {
			runtimeService
		  .createSignalEvent("PaymentSignal") 
		  .setVariables(map)
		  .send();
		}
		catch (Exception e) {
			System.out.println("No signal");
		}
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

	/*
	public void notify(DelegateTask delegateTask) {

		boolean paymentSuccess = true;
		paymentSuccess = checkPayment();
		
		if (paymentSuccess) {
			delegateTask.setVariable("payment_success", true);
		}
		else {
			delegateTask.setVariable("payment_success", false);
		}
		
		runtimeService
		  .createSignalEvent("Payment info") 
		  .send();
		
	}*/
}

/*List<FormFieldDto> formFields = 
(List<FormFieldDto>) execution.getVariable("payment");
List<FormFieldDto> files =(List<FormFieldDto>) execution.getVariable("files");
*/


/*User writer = userRepository.findUserByUsername(files.get(1).getFieldValue());		
Card card = cardReposotory.findOneByOwner(writer);

if (card == null ) {
paymentSuccess = false;
System.out.println("card je null");
}
for (FormFieldDto f : formFields) {
if (f.getFieldId().equals("card_holder_id")) {
if (f.getFieldValue().trim().isEmpty() 
		|| !f.getFieldValue().equals(card.getCardHolder())) {
	System.out.println("card holder je false");
	paymentSuccess = false;
}
}
if (f.getFieldId().equals("card_number_id")) {
if (f.getFieldValue().trim().isEmpty() 
		|| !org.springframework.security.crypto.bcrypt.BCrypt.checkpw(f.getFieldValue(),card.getCardNumber())) {
	paymentSuccess = false;
	System.out.println("card number je false");
}
}
if (f.getFieldId().equals("cvv_id")) {
if (f.getFieldValue().trim().isEmpty() 
		|| !org.springframework.security.crypto.bcrypt.BCrypt.checkpw(f.getFieldValue(),card.getCvv())) {
	paymentSuccess = false;
	System.out.println("card cvv je false");
}
}
if (f.getFieldId().equals("expiration_date_id")) {
if (f.getFieldValue().trim().isEmpty() 
		|| !f.getFieldValue().equals(card.getExpirationDate())) {
	paymentSuccess = false;
	System.out.println("card date je false");
}
}
}
*/
