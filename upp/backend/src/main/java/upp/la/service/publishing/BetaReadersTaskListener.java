package upp.la.service.publishing;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.dto.BetaReaderDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BetaReadersTaskListener implements TaskListener {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;
    @Override
    public void notify(DelegateTask delegateTask) {
        List<User> betaReaders = userRepository.findUsersByRole(Role.BETA_READER);
        ArrayList<BetaReaderDto> betaReaderDtos = new ArrayList<>();
        int counter = 1;
        for(User u: betaReaders) {
            BetaReaderDto b = new BetaReaderDto();
            b.setId((long)counter);
            b.setName(u.getUsername());
            betaReaderDtos.add(b);
            counter++;
        }

        String taskId = delegateTask.getId();
        FormService formService = delegateTask.getProcessEngineServices().getFormService();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);

        List<FormField> properties = taskFormData.getFormFields();

        if (properties != null) {

            for (FormField field : properties) {
                if (field.getId().equals("betaReadresId")) {
                    HashMap<String, String> items = (HashMap<String, String>) field.getType().getInformation("values");
                    for(BetaReaderDto g: betaReaderDtos) {
                        String valueId = g.getId().toString();
                        String valueName = g.getName();
                        items.put(valueId, valueName);
                    }
                    System.out.println(items);
                }
            }
        }
    }
}
