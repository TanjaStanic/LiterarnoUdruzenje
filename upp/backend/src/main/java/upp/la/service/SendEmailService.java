package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import upp.la.util.Requests;
import upp.la.dto.FormFieldDto;
import upp.la.model.auth.ConfirmationToken;
import upp.la.model.EmailTemplate;
import upp.la.model.User;
import upp.la.repository.ConfirmationTokenRepository;
import upp.la.repository.UserRepository;

import java.util.List;

@Component
public class SendEmailService implements JavaDelegate{

	
	@Autowired 
	private Environment env;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	ConfirmationTokenRepository confirmationTokenRepository;
	
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

		User user = userRepository.findUserByEmail(email);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		String processId = execution.getProcessInstanceId();
		confirmationToken = confirmationTokenRepository.save(confirmationToken);
		String message = env.getProperty("app.registration-url") + confirmationToken.getToken() 
			+ "/" + processId;
		System.out.println("link je " + message);
		
		EmailTemplate emailTempl =
		        new EmailTemplate(
		        		email,
		            EmailTemplate.VERIFICATION_SUBJECT(),
		            EmailTemplate.VERIFICATION_MESSAGE(message));

		 Requests.sendEmail(emailTempl);
	}

	

}
