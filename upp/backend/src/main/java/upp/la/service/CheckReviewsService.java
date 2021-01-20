package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.UserRepository;
import upp.la.service.internal.RegistrationApplicationResponseServiceInt;


@Service
public class CheckReviewsService implements JavaDelegate{

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RegistrationApplicationResponseServiceInt regAppResponseServiceInt;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		RegistrationApplication app =(RegistrationApplication) execution.getVariable("registration_application");
		
		Integer numAccepted = 0;
		Integer numRejected = 0;
		
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
					execution.setVariable("response", "need_more_documents");
					app.setFinalResponse(ApplicationResponse.LACKING_MATERIAL);
				}
			}
			
			if (numAccepted==3) {
				execution.setVariable("response", "accepted");
				app.setFinalResponse(ApplicationResponse.APPROVED);
			}
			else if (numRejected>1) {
				execution.setVariable("response", "denied");
				app.setFinalResponse(ApplicationResponse.NOT_APPROVED);
			} 
			else if (numAccepted==2 && numRejected==1) {
				execution.setVariable("response", "review_again");
			}
			
			
		} 
		
	}

}
