package upp.la.service.plagiarism;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.repository.BookRepository;

@Service
public class ValidationService implements JavaDelegate {

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormFieldDto> formFields = 
        		(List<FormFieldDto>) execution.getVariable("Plagiarism complaint");
		
		Boolean validated = true;
		Book myBook = null;
		List<Book> books = new ArrayList<Book>();

		for (FormFieldDto f : formFields) {
			
			//check if book title is empty
			if (f.getFieldId().equals("bookTitleId")) {				
				try {
					myBook = bookRepository.findBookByTitle(f.getFieldValue());
					books.add(myBook);
				}
				catch (BpmnError e) {
					System.out.println("There is no book with title: " + f.getFieldValue());
					throw new BpmnError("ValidationErrorCode");
				}
				
				if ((f.getFieldValue().trim().isEmpty()) || myBook==null) {
					validated = false;
				}
				
			}
			//check if book author is empty
			if (f.getFieldId().equals("bookAuthorId")) {
				if ((f.getFieldValue().trim().isEmpty())) {
					validated = false;
				}	
			}
			
			//check if writer book is empty or in base
			if (f.getFieldId().equals("writerBookTitleId")) {
				try {
					myBook = bookRepository.findBookByTitle(f.getFieldValue());
					books.add(myBook);
				}
				catch (BpmnError e) {
					System.out.println("There is no book with title: " + f.getFieldValue());
					throw new BpmnError("ValidationErrorCode");
				}
				
				if ((f.getFieldValue().trim().isEmpty()) || myBook==null) {
					validated = false;
				}		
			}

		}

		
		execution.setVariable("validated", validated);
		execution.setVariable("books", books);
		
	}

}
