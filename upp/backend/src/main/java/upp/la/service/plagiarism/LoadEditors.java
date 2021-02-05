package upp.la.service.plagiarism;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.model.User;
import upp.la.repository.UserRepository;

import java.util.ArrayList;

@Service
public class LoadEditors implements JavaDelegate{
	
	@Autowired
	UserRepository userRepisotory;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		/*ArrayList<User> allEditors = null;
	
		try {
			allEditors = (ArrayList<User>) userRepisotory.findAll();
			
		}
		catch (BpmnError e) {
			System.out.println("No editors");
			throw new BpmnError("LoadEditorError");
		}
		execution.setVariable("editors", allEditors);*/
	}
	

	

}
