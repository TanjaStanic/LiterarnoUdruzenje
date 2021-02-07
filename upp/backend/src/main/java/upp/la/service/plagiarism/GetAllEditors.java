package upp.la.service.plagiarism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;
import upp.la.repository.PlagiarismComplaintRepository;
import upp.la.repository.PlagiarismComplaintResponseRepository;
import upp.la.repository.UserRepository;

@Service
@Component
public class GetAllEditors implements TaskListener{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlagiarismComplaintRepository plagiarismComplaintRepository;

    @Autowired
    PlagiarismComplaintResponseRepository plagiarismComplaintResponseRepository;

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
		List<User> editors = userRepository.findUsersByRole(Role.EDITOR);
        List<User> used = new ArrayList<>();
        Long id = Long.parseLong((String) delegateTask.getVariable("plagiarismComplaintId"));
        Optional<PlagiarismComplaint> plagiarismComplaint = plagiarismComplaintRepository.findById(id);
        if(!plagiarismComplaint.get().getResponses().isEmpty()) {
            for(PlagiarismComplaintResponse response : plagiarismComplaint.get().getResponses()) {
                used.add(response.getEditor());
            }
        }

        ArrayList<BetaReaderDto> editorsDtos = new ArrayList<>();
        for(User u: editors) {
            if(!used.contains(u)) {
                BetaReaderDto b = new BetaReaderDto();
                b.setId(u.getId());
                b.setName(u.getFirstName() + " " + u.getLastName());
                editorsDtos.add(b);
            }
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
