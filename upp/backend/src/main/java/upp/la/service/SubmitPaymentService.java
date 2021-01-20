package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class SubmitPaymentService implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		boolean paymentSuccess = true;
		
		if (paymentSuccess) {
			execution.setVariable("payment_success", true);
		}
		else {
			execution.setVariable("payment_success", false);
		}
	}

}
