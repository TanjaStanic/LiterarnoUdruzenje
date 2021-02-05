package upp.la.service.plagiarism;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.UserRepository;

@Service
public class MakeChosenEditorsList implements JavaDelegate {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormFieldDto> formFields = 
        		(List<FormFieldDto>) execution.getVariable("Choose editors");
		List<User> allEditors = userRepository.findUsersByRole(Role.EDITOR);
		
		//editors  - varijabla u koju se smjestaju useri koji mogu da budu dodani i u narednom krugu
		//			ako je user jednom izabran da bude editor, a ne ocijeni na vrijeme, drugi put nece moci 
		//			da bude izabran
		
		//chosen editors - varijabla u kojo jse smjestaju useri kojima je dodeljeno pregledanje knjiga
		
		List<User> chosenEditors = new ArrayList<User>();
		User u = null;
		for (FormFieldDto f : formFields) {
			System.out.println("User koji mi treba za listu editora " + f.getFieldValue());
			u = userRepository.findUserById(Long.parseLong(f.getFieldValue()));
			chosenEditors.add(u);
			allEditors.remove(u);
		}
		
		int numEditors = chosenEditors.size();
		// execution.setVariable("chosenEditors", chosenEditors);
		// execution.setVariable("editors", allEditors);
		execution.setVariable("numEditors", numEditors);
	}

}
