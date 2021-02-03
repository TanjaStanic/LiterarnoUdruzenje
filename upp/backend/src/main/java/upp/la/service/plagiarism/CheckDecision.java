package upp.la.service.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.error.ErrorMessages;
import upp.la.model.Book;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.repository.BookRepository;
import upp.la.repository.PlagiarismComplaintRepository;
import upp.la.repository.PlagiarismComplaintResponseRepository;
import upp.la.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class CheckDecision implements JavaDelegate {

  @Autowired UserRepository userRepository;

  @Autowired PlagiarismComplaintRepository complaintRepository;

  @Autowired PlagiarismComplaintResponseRepository complaintResponseRepository;

  @Autowired BookRepository bookRepository;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {

    List<FormFieldDto> plagiarismForm = (List<FormFieldDto>) delegateExecution.getVariable("Plagiarism complaint");

    String title = plagiarismForm.get(2).getFieldValue();

    Optional<Book> writersBook = bookRepository.findByTitle(title);

    if(!writersBook.isPresent()) {
      throw new EntityNotFound("Book does not exist");
    }

    Optional<PlagiarismComplaint> complaint = complaintRepository.findByWritersBook(writersBook.get());

    if(!complaint.isPresent()) {
      throw new EntityNotFound("Complaint does not exist");
    }

    int positiveResponses = (int) delegateExecution.getVariable("decisionsMultiInstancePositive");

    int negativeResponses = (int) delegateExecution.getVariable("decisionsMultiInstanceNegative");

    if (positiveResponses==0 && negativeResponses!=0) {
      System.out.println("NOT PLAGIARISED");

      complaint.get().setFinalResponse(false);

      complaintRepository.save(complaint.get());

      delegateExecution.setVariable("uniform", true);
    } else if (positiveResponses!=0 && negativeResponses==0) {
      System.out.println("PLAGIARISED");

      complaint.get().setFinalResponse(true);

      complaintRepository.save(complaint.get());
      delegateExecution.setVariable("uniform", true);
    } else {
      System.out.println("NOT UNIFORM OR EMPTY");

      delegateExecution.setVariable("uniform", false);
    }
  }
}
