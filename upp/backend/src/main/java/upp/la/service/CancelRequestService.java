package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.model.User;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplication;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.UserRepository;

@Service
public class CancelRequestService implements JavaDelegate{

	@Autowired
	RegistrationApplicationRepository regAppRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		RegistrationApplication app =(RegistrationApplication) execution.getVariable("registration_application");
		app.setFinalResponse(ApplicationResponse.NOT_APPROVED);
		
		User writer = userRepository.findOneByRegistrationApplication(app);
		writer.setConfirmed(false);
		
		userRepository.save(writer);
		regAppRepository.save(app);

		
	}

}
