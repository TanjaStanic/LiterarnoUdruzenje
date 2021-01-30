package upp.la.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
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
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.UserRepository;

@Service
public class CancelRequestService implements JavaDelegate{

	@Autowired
	RegistrationApplicationRepository regAppRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RuntimeService runtimeService;
	
	@Override
	public void execute(DelegateExecution execution) throws EntityNotFound {
		
		System.out.println("In cancel request");
		List<FormFieldDto> files =(List<FormFieldDto>) execution.getVariable("files");
		User writer = userRepository.findUserByUsername(files.get(1).getFieldValue());

		
		RegistrationApplication app = null;	
		try {
			app = regAppRepository.findOneByWriter(writer);
		}
		catch (EntityNotFoundException e) {
			throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		}
		
		try {
			Long id = (Long) runtimeService.getVariableLocal(execution.getId(), "usernameId");
			System.out.println(id);
		}
		catch (ProcessEngineException  e) {
			System.out.println("Nije preuzeto username");
		}
		try {
			String s = (String) runtimeService.getVariableLocal(execution.getId(), "requestStatus");
			System.out.println(s);
			if (s.equals("false")) {
				app.setFinalResponse(ApplicationResponse.NOT_APPROVED);
				writer.setConfirmed(false);
			}
			else if (s.equals("true")){
				app.setFinalResponse(ApplicationResponse.APPROVED);
				writer.setConfirmed(true);
			}
		}
		catch (ProcessEngineException  e) {
			System.out.println("Nije preuzeto s");
		}
		
		
		
		
		
		
		
		
		userRepository.save(writer);
		regAppRepository.save(app);

		
	}

}
