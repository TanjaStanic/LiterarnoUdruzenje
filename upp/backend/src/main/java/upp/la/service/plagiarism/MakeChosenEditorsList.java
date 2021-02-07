package upp.la.service.plagiarism;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;
import upp.la.repository.PlagiarismComplaintRepository;
import upp.la.repository.PlagiarismComplaintResponseRepository;
import upp.la.repository.UserRepository;

@Service
public class MakeChosenEditorsList implements JavaDelegate {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PlagiarismComplaintRepository plagiarismComplaintRepository;

	@Autowired
	PlagiarismComplaintResponseRepository plagiarismComplaintResponseRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormFieldDto> formFields = 
        		(List<FormFieldDto>) execution.getVariable("Choose editors");
		List<User> allEditors = userRepository.findUsersByRole(Role.EDITOR);
		Long id = Long.parseLong((String) execution.getVariable("plagiarismComplaintId"));
		Optional<PlagiarismComplaint> plagiarismComplaint = plagiarismComplaintRepository.findById(id);
		//editors  - varijabla u koju se smjestaju useri koji mogu da budu dodani i u narednom krugu
		//			ako je user jednom izabran da bude editor, a ne ocijeni na vrijeme, drugi put nece moci 
		//			da bude izabran
		
		//chosen editors - varijabla u kojo jse smjestaju useri kojima je dodeljeno pregledanje knjiga
		
		List<User> chosenEditors = new ArrayList<User>();
		User u = null;
		for (FormFieldDto f : formFields) {
			u = userRepository.findUserById(Long.parseLong(f.getFieldValue()));
			PlagiarismComplaintResponse response = new PlagiarismComplaintResponse();
			response.setEditor(u);
			response.setComment("");
			response.setPlagiarismComplaint(plagiarismComplaint.get());
			plagiarismComplaintResponseRepository.save(response);
			plagiarismComplaint.get().getResponses().add(response);
			chosenEditors.add(u);
			allEditors.remove(u);
		}
		plagiarismComplaintRepository.save(plagiarismComplaint.get());
		int numEditors = chosenEditors.size();
		// execution.setVariable("chosenEditors", chosenEditors);
		// execution.setVariable("editors", allEditors);
		execution.setVariable("numEditors", numEditors);
	}

}
