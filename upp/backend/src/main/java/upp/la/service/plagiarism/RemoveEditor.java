package upp.la.service.plagiarism;

import java.util.ArrayList;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.model.User;
import upp.la.repository.UserRepository;

@Service
public class RemoveEditor implements JavaDelegate{

	@Autowired 
	RuntimeService runtimeService;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ArrayList<User> allEditors = (ArrayList<User>)execution.getVariable("editors");
		ArrayList<User> chosenEditors = (ArrayList<User>)execution.getVariable("chosenEditors");

		String username = "";
		User editor = new User();
		
		try {
			username = (String) runtimeService.getVariableLocal(execution.getId(), "usernameId");
			System.out.println(username);
		}
		catch (ProcessEngineException  e) {
			System.out.println("Username failed");
		}
		editor = userRepository.findByUsername(username);
		
		chosenEditors.remove(editor);
		allEditors.remove(editor);
		
		execution.setVariable("chosenEditors", chosenEditors);
		execution.setVariable("editors", allEditors);
	}

}
