package upp.la.service.plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.repository.UserRepository;

import org.camunda.bpm.engine.identity.User;
import java.util.ArrayList;

@Service
public class LoadEditors implements JavaDelegate{

	@Autowired
    private IdentityService identityService;
	
	@Autowired
	UserRepository userRepisotory;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ArrayList<User> allEditorsCamunda = null;
		ArrayList<upp.la.model.User> allEditors = null;
		User chiefEditor = null;

		
		//first from the list is the CHIEF editor
		
		try {
			allEditorsCamunda = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("editors").list();
			chiefEditor = (User)identityService.createUserQuery().memberOfGroup("editors").list().get(0);
			allEditors = (ArrayList<upp.la.model.User>) userRepisotory.findAll();
			
		}
		catch (BpmnError e) {
			System.out.println("No editors");
		}
		execution.setVariable("allEditors", allEditors);
		execution.setVariable("allEditorsCamunda", allEditorsCamunda);
		execution.setVariable("chiefEditor", chiefEditor);
	}
	

	

}
