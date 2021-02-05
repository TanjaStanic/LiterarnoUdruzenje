package upp.la.service.plagiarism;
import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import upp.la.model.User;

import org.camunda.bpm.engine.impl.form.type.EnumFormType;

@Service
public class GetAllEditors implements TaskListener{

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		ArrayList<User> editors = (ArrayList<User>)delegateTask.getExecution().getVariable("editors");		
		
		for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().contains("chosenEditorsId")) {
            	
            	
                EnumFormType enumFormType = (EnumFormType) f.getType();
                enumFormType.getValues().clear();
                for (User editor : editors) {
                    enumFormType.getValues().put(editor.getId().toString(), editor.getFirstName().concat(" ").concat(editor.getLastName()) );
                }
            }
        }
		
		
		
	}

}
