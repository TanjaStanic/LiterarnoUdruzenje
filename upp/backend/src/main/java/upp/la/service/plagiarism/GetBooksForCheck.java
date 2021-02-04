package upp.la.service.plagiarism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.form.FormField;

import upp.la.model.Book;


public class GetBooksForCheck implements TaskListener{

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<Book> books = (ArrayList<Book>) delegateTask.getVariable("books");
		
		for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().equals("books")) {
            	HashMap<String, String> items = (HashMap<String, String>) f.getType().getInformation("values");

                for (Book b : books) {
                	items.put(b.getId().toString(), b.getTitle());
                }
            }
        }
	}

}
