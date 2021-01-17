package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import upp.la.dto.FormFieldDto;
import upp.la.model.auth.ConfirmationToken;
import upp.la.model.EmailTemplate;
import upp.la.model.User;
import upp.la.repository.UserRepository;
import upp.la.util.Requests;

import java.util.List;


public class SendEmailService implements JavaDelegate{

	
	@Autowired 
	private Environment env;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String email = "";
		List<FormFieldDto> formFields = 
        		(List<FormFieldDto>) execution.getVariable("registration");
		
		for (FormFieldDto f : formFields) {
			if (f.getFieldId().equals("emailId")) {
				email = f.getFieldValue();
			}
		}
		
		User user = userRepository.findByEmail(email);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		
		String processId = execution.getProcessInstanceId();
		String message = env.getProperty("app.registration-url") + confirmationToken.getConfirmationToken() 
			+ "/" + processId;
		
		EmailTemplate emailTempl =
		        new EmailTemplate(
		        		email,
		            EmailTemplate.VERIFICATION_SUBJECT(),
		            EmailTemplate.VERIFICATION_MESSAGE(message));

		 Requests.sendEmail(emailTempl);
	}

	

}
