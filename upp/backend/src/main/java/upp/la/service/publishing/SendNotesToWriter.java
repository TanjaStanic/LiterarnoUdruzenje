package upp.la.service.publishing;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;

import java.util.List;

@Service
@Component
public class SendNotesToWriter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Lecturer notes typos");
        String comment = "";
        for(FormFieldDto f: dtos) {
            if(f.getFieldId().equals("commentId")) {
                comment = f.getFieldValue();
            }
        }
        //Poslati mail piscu sa komentarima
    }
}
