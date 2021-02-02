package upp.la.service.plagiarism;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;
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

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    //TODO: Get and set true id of complaint
    Long complaintId = (Long) delegateExecution.getVariable("complaintId");

    Optional<PlagiarismComplaint> complaint = complaintRepository.findById(complaintId);

    if(!complaint.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    int positiveResponses = complaintResponseRepository.countByPlagiarismComplaintAndPlagiarisedResponseIsTrue(complaint.get());

    int totalResponses = complaintResponseRepository.countByPlagiarismComplaint(complaint.get());

    if (positiveResponses==0 && totalResponses!=0) {
      System.out.println("NOT PLAGIARISED");

      complaint.get().setFinalResponse(false);

      complaintRepository.save(complaint.get());

      delegateExecution.setVariable("uniform", true);
    } else if (positiveResponses==totalResponses && totalResponses!=0) {
      System.out.println("PLAGIARISED");

      complaint.get().setFinalResponse(true);

      complaintRepository.save(complaint.get());
      delegateExecution.setVariable("uniform", true);
    } else {
      System.out.println("NOT UNIFORM");

      delegateExecution.setVariable("uniform", false);
    }
  }
}
