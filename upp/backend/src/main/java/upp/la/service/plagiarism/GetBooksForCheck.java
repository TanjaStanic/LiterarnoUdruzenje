package upp.la.service.plagiarism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.form.FormField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.repository.BookRepository;


@Component
@Service
public class GetBooksForCheck implements TaskListener{

	@Autowired
	BookRepository bookRepository;

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		// List<Book> books = (ArrayList<Book>) delegateTask.getVariable("books");
		List<Book> books = new ArrayList<>();
		List<FormFieldDto> formFields =
				(List<FormFieldDto>) delegateTask.getVariable("Plagiarism complaint");

		for(FormFieldDto f : formFields) {
			if(f.getFieldId().equals("bookTitleId")) {
				System.out.println(f.getFieldValue());
				Book book = bookRepository.findBookByTitle(f.getFieldValue());
				books.add(book);
			} else if(f.getFieldId().equals("writerBookTitleId")) {
				System.out.println(f.getFieldValue());
				Book book = bookRepository.findBookByTitle(f.getFieldValue());
				books.add(book);
			}
		}
		
		for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("books")) {
            	HashMap<String, String> items = (HashMap<String, String>) f.getType().getInformation("values");

                for (Book b : books) {
                	items.put(b.getTitle(), b.getDocument().getFileUrl());
                }
            }
        }
	}

}
