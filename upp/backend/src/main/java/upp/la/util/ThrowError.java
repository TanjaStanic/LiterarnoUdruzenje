package upp.la.util;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import upp.la.model.EmailTemplate;
import upp.la.model.User;
import upp.la.repository.UserRepository;

@Component
public class ThrowError implements JavaDelegate {

  @Autowired UserRepository userRepository;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    System.out.println("Service Task Error.");

    User admin = userRepository.findByUsername("admin");

    EmailTemplate email = new EmailTemplate("Service task error", "Service task error.");

    email.setAddress(admin.getEmail());

    Requests.sendEmail(email);

    throw new BpmnError("ServiceTaskError");
  }
}
