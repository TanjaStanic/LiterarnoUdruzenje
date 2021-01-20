package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import upp.la.dto.FormFieldDto;

import java.util.List;

@Service
public class SaveDocumentsService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> files =
                (List<FormFieldDto>) delegateExecution.getVariable("files");

        System.out.println(files.get(0).getFieldValue());
    }
}
