package upp.la.service;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import upp.la.model.EmailTemplate;
import upp.la.model.registration.ApplicationResponse;
import upp.la.util.Requests;

@Getter
@Setter
@Component
public class EmailService implements JavaDelegate {

  private String Type;

  private String Address;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    if (this.getType().equals("RegistrationReviewLecturers")) {
      EmailTemplate email = EmailTemplate.RegistrationApplicationReviewInviteEmail();

      email.setAddress(this.getAddress());

      Requests.sendEmail(email);
    } else if (this.getType().equals("RegistrationReviewResponseWriter")) {
      ApplicationResponse response =
          (ApplicationResponse) execution.getVariable("registration_response");

      EmailTemplate email = EmailTemplate.ApplicationResponseEmail(response);

      email.setAddress(this.getAddress());

      Requests.sendEmail(email);
    } else if (this.getType().equals("RegistrationReviewMaterialWriter")) {
      Integer deadlineDays = (Integer) execution.getVariable("deadline_days");

      EmailTemplate email = EmailTemplate.ApplicationResponseLackingMaterialEmail(deadlineDays);

      email.setAddress(this.getAddress());

      Requests.sendEmail(email);
    } else if (this.getType().equals("RegistrationApplicationPaymentWriter")) {
      Integer deadlineDays = (Integer) execution.getVariable("deadline_days");

      EmailTemplate email = EmailTemplate.RegistrationApplicationPayment(deadlineDays);

      email.setAddress(this.getAddress());

      Requests.sendEmail(email);
    } else if (this.getType().equals("RegistrationApplicationPaymentFailedWriter")) {
      EmailTemplate email = EmailTemplate.RegistrationApplicationPaymentFailed();

      email.setAddress(this.getAddress());

      Requests.sendEmail(email);
    } else {
      throw new BpmnError("EmailError");
    }
  }
}
