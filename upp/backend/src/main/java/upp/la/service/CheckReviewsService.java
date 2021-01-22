package upp.la.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.error.ErrorMessages;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.UserRepository;
import upp.la.service.internal.RegistrationApplicationResponseServiceInt;


@Service
public class CheckReviewsService implements JavaDelegate{

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RegistrationApplicationRepository regAppRepository;
	
	@Autowired 
	RegistrationApplicationResponseServiceInt regAppResponseServiceInt;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("in CheckReviewsService");
		List<FormFieldDto> files =(List<FormFieldDto>) execution.getVariable("files");
		User writer = userRepository.findUserByUsername(files.get(1).getFieldValue());
		
		RegistrationApplication app = null;
		try {
			app = regAppRepository.findOneByWriter(writer);
		}
		catch (EntityNotFoundException e) {
			throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		}
		
		Integer numAccepted = 0;
		Integer numRejected = 0;
		Integer numMoreDocuments = 0;
		
		int numReviews = app.getResponses().size();
		if (numReviews == 3) {
			for (RegistrationApplicationResponse r : app.getResponses()) {
				if (r.getResponse().equals(ApplicationResponse.APPROVED)) {
					numAccepted++;
				}
				else if (r.getResponse().equals(ApplicationResponse.NOT_APPROVED)) {
					numRejected++;
				}
				else if (r.getResponse().equals(ApplicationResponse.LACKING_MATERIAL)) {
					numMoreDocuments++;
					
				}
			}
			// aproved only when all of them voted for ACCEPT
			if (numAccepted==3) {
				execution.setVariable("response", "accepted");
				app.setFinalResponse(ApplicationResponse.APPROVED);
			}
			// not_aproved when more then half review REJECTED
			else if (numRejected>=2) {
				execution.setVariable("response", "denied");
				app.setFinalResponse(ApplicationResponse.NOT_APPROVED);
			} 
			//not denied, one or two lectors voted for need more documents
			else if (numMoreDocuments>=1) {
				execution.setVariable("response", "need_more_documents");
				app.setFinalResponse(ApplicationResponse.LACKING_MATERIAL);
			}
			// not defined in specification
			else if (numAccepted==2 && numRejected==1) {
				execution.setVariable("response", "review_again");
			}
			
			
		} 

		System.out.println("Ovo je counter varijabla " + execution.getVariable("COUNTER").toString());
	}

}
