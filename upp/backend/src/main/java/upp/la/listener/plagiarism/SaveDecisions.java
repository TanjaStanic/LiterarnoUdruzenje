package upp.la.listener.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import upp.la.dto.FormFieldDto;

import java.util.List;

@Component
public class SaveDecisions implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    List<FormFieldDto> decisionForm =
        (List<FormFieldDto>) execution.getVariable("Decide if plagiarised");

    int decisionsMultiInstancePositive = (int) execution.getVariable("decisionsMultiInstancePositive");

    int decisionsMultiInstanceNegative = (int) execution.getVariable("decisionsMultiInstanceNegative");

    String decision = decisionForm.get(0).getFieldValue();

    if (decision.equals("true")) {
      decisionsMultiInstancePositive += 1;
    } else {
      decisionsMultiInstanceNegative += 1;
    }

    execution.setVariable("decisionsMultiInstancePositive", decisionsMultiInstancePositive);

    execution.setVariable("decisionsMultiInstanceNegative", decisionsMultiInstanceNegative);
  }
}
