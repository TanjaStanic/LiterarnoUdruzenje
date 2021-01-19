package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.error.ErrorMessages;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.auth.ConfirmationToken;
import upp.la.model.User;
import upp.la.repository.ConfirmationTokenRepository;
import upp.la.repository.UserRepository;

@Service
public class VerifyAccountService implements JavaDelegate{

	
	@Autowired 
	ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired 
	UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String confirmationToken = execution.getVariable("validation_token").toString();
	      System.out.println("savedToken> "+confirmationToken );
	      ConfirmationToken token = null; 
	      
	      try{
	    	  
	    	  token = confirmationTokenRepository.findOneByToken(confirmationToken);
		}catch(Exception e) {
			System.out.println("exception");
		}
		    if (token == null) {
		      throw new EntityNotFound(ErrorMessages.TOKEN_ERROR());
		    }

		    User user = userRepository.findByEmail(token.getUser().getEmail());

		    if (user == null) {
		      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
		    }

		    user.setActivated(true);

		    userRepository.save(user);
		    confirmationTokenRepository.delete(token);
		
	}

}
