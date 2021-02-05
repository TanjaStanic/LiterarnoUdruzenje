package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.EmailTemplate;
import upp.la.model.User;
import upp.la.repository.UserRepository;
import upp.la.util.Requests;

import java.util.List;

@Service
@Component
public class SendNotesToWriter implements JavaDelegate {
  @Autowired UserRepository userRepository;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    List<FormFieldDto> dtos =
        (List<FormFieldDto>) delegateExecution.getVariable("Lecturer notes typos");
    String comment = "";
    for (FormFieldDto f : dtos) {
      if (f.getFieldId().equals("commentId")) {
        comment = f.getFieldValue();
      }
    }

    List<FormFieldDto> tmp =
        (List<FormFieldDto>) delegateExecution.getVariable("Book details form");

    User user = new User();
    for (FormFieldDto f : tmp) {
      if (f.getFieldId().equals("writerUsernameId")) {
        user = userRepository.findUserByUsername(f.getFieldValue());
      }
    }

    EmailTemplate email = EmailTemplate.PublishingLecturerNotesWriter(comment);

    email.setAddress(user.getEmail());

    //Requests.sendEmail(email);
  }
}
