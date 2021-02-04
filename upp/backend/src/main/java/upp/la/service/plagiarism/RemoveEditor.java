package upp.la.service.plagiarism;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class RemoveEditor implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ArrayList<User> editors = (ArrayList<User>)execution.getVariable("allEditorsCamunda");
		
	}

}
