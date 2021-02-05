package upp.la.service.plagiarism;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.UserRepository;

@Service
public class SetChiefEditor implements JavaDelegate{

	@Autowired
	UserRepository userRepisotory;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		User chiefEditor = null;
		try {
			chiefEditor = (User)execution.getVariable("chief") ;
		}
		catch (BpmnError e) {
			System.out.println("No editor");
		}
		chiefEditor.setRole(Role.CHIEF_EDITOR);
		chiefEditor = userRepisotory.save(chiefEditor);
		execution.setVariable("chiefEditor", chiefEditor);

	}

}
