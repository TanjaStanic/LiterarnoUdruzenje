package upp.la.service.plagiarism;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.repository.BookRepository;
import upp.la.repository.PlagiarismComplaintRepository;
import upp.la.repository.UserRepository;

import java.util.List;

@Service
@Component
public class SetChiefEditor implements JavaDelegate{

	@Autowired
	UserRepository userRepisotory;

	@Autowired
	PlagiarismComplaintRepository plagiarismComplaintRepository;

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormFieldDto> formFields =
				(List<FormFieldDto>) execution.getVariable("Plagiarism complaint");
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

		Book writerBook = new Book();
		Book plagiarised = new Book();
		PlagiarismComplaint plagiarismComplaint = new PlagiarismComplaint();

		for(FormFieldDto f : formFields) {
			if(f.getFieldId().equals("writerBookTitleId")) {
				writerBook = bookRepository.findBookByTitle(f.getFieldValue());
			} else if(f.getFieldId().equals("bookTitleId")) {
				plagiarised = bookRepository.findBookByTitle(f.getFieldValue());
			}
		}

		plagiarismComplaint.setPlagiarisedBook(plagiarised);
		plagiarismComplaint.setWritersBook(writerBook);
		plagiarismComplaint.setFinalResponse(false);
		PlagiarismComplaint ret = plagiarismComplaintRepository.save(plagiarismComplaint);
		execution.setVariable("plagiarismComplaintId", ret.getId().toString());
	}

}
