package upp.la.service.plagiarism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import upp.la.dto.BetaReaderDto;
import upp.la.model.Role;
import upp.la.model.User;

import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import upp.la.repository.UserRepository;

@Service
@Component
public class GetAllEditors implements TaskListener{

    @Autowired
    UserRepository userRepository;
	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<User> editors = userRepository.findUsersByRole(Role.EDITOR);
        ArrayList<BetaReaderDto> editorsDtos = new ArrayList<>();
        for(User u: editors) {
            BetaReaderDto b = new BetaReaderDto();
            b.setId(u.getId());
            b.setName(u.getFirstName() + " " + u.getLastName());
            editorsDtos.add(b);
        }
		
		for (FormField f : taskFormFields.getFormFields()) {
            if (f.getId().contains("chosenEditorsId")) {
                HashMap<String, String> items = (HashMap<String, String>) f.getType().getInformation("values");
                for(BetaReaderDto g: editorsDtos) {
                    String valueId = g.getId().toString();
                    String valueName = g.getName();
                    items.put(valueId, valueName);
                }
                System.out.println(items);
            }
        }
		
	}

}
