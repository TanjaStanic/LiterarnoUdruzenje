package upp.la.service;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.ThrowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import upp.la.util.Requests;
import upp.la.dto.FormFieldDto;
import upp.la.model.auth.ConfirmationToken;
import upp.la.model.EmailTemplate;
import upp.la.model.Role;
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
		System.out.println("EmailService");
		
		ThrowEvent messageEvent = (ThrowEvent) execution.getBpmnModelElementInstance();
	    MessageEventDefinition messageEventDefinition = (MessageEventDefinition) messageEvent
	        .getEventDefinitions().iterator().next();
	    String receivingMessageName = messageEventDefinition.getMessage().getName();
	    System.out.println("message name je: " + receivingMessageName);
	    
	    
	    String mail = "";
	    try {
	    	List<FormFieldDto> files =(List<FormFieldDto>) execution.getVariable("files");
			User writer = userRepository.findUserByUsername(files.get(1).getFieldValue());
			mail = writer.getEmail();
	    }
        catch(Exception e) {
        	System.out.println("No user. Tek se korisnik registruje");
			throw new BpmnError("EmailError");
        }
		
	    
	    //send email to confirm registration
	    if (receivingMessageName.equals("send_verification_email")) {
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
	    
		// NOTIFY LECTORS
	    else if (receivingMessageName.equals("RegistrationReviewLecturers")) {
		      EmailTemplate email = EmailTemplate.RegistrationApplicationReviewInviteEmail();
		      List<User> lectors = userRepository.findUsersByRole(Role.LECTURER);

		      for (User l : lectors) {
		    	  System.out.println("saljem mail na: " + l.getEmail());
		    	  email.setAddress(l.getEmail());
		    	  Requests.sendEmail(email);
		      }
		 }
	    // ACCEPTED - PAY  
	    else if (receivingMessageName.equals("RegistrationApplicationPaymentWriter")) {
	        EmailTemplate email = EmailTemplate.RegistrationApplicationPayment(14);
	      
			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);

	    }
	    // NEED MORE MATERIAL
	    else if (receivingMessageName.equals("RegistrationReviewMaterialWriter")) {
	        EmailTemplate email = EmailTemplate.ApplicationResponseLackingMaterialEmail(14);
	      
			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);

	    }
	    //FAILED TRANSACTION
	    else if (receivingMessageName.equals("RegistrationApplicationNotAccepted")) {
	    	EmailTemplate email = EmailTemplate.RegistrationApplicationNotAccepted();

	        email.setAddress(mail);
	        System.out.println("mail glasi: " + email.getMessage());
	        Requests.sendEmail(email);
	    }
	    //FAILED PAYMENT
	    else if (receivingMessageName.equals("RegistrationApplicationPaymentFailedWriter")) {
	    	EmailTemplate email = EmailTemplate.RegistrationApplicationPaymentFailed();

	        email.setAddress(mail);
	        System.out.println("mail glasi: " + email.getMessage());
	        Requests.sendEmail(email);
	    }
		//PUBLISHING DECLINED BEFORE MANUSCRIPT
		else if (receivingMessageName.equals("BookPublishingNotifyWriterDeclined")) {
			String reason = (String) execution.getVariable("reason");

			EmailTemplate email = EmailTemplate.PublishingDeclinedBeforeManuscript(reason);

			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);
		}
		//PUBLISHING NOTIFY WRITER EXPIRED
		else if (receivingMessageName.equals("BookPublishingNotifyWriterExpired")) {
			EmailTemplate email = EmailTemplate.PublishingNotifyWriterExpired();

			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);
		}
		//PLAGIARISM NOTIFY CHIEF EDITOR
		else if (receivingMessageName.equals("PlagiarismComplaintNotifyChiefEditor")) {
			EmailTemplate email = EmailTemplate.PlagiarismComplaintNotifyChiefEditor();

			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);
		}
		//PLAGIARISM NOTIFY WRITER DECISION
		else if (receivingMessageName.equals("PlagiarismNotifyWriterDecision")) {
			EmailTemplate email = EmailTemplate.PlagiarismNotifyWriterDecision("TODO: DECISIONS");

			email.setAddress(mail);
			System.out.println("mail glasi: " + email.getMessage());
			Requests.sendEmail(email);
		}
	    else {
			throw new BpmnError("EmailError");
		}
	}
}
