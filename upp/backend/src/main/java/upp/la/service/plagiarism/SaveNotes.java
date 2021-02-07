package upp.la.service.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.User;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;
import upp.la.repository.PlagiarismComplaintRepository;
import upp.la.repository.PlagiarismComplaintResponseRepository;
import upp.la.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class SaveNotes implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlagiarismComplaintRepository plagiarismComplaintRepository;

    @Autowired
    PlagiarismComplaintResponseRepository plagiarismComplaintResponseRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> formFields =
                (List<FormFieldDto>) delegateExecution.getVariable("Make notes");
        String comment = "";
        User user = new User();
        for(FormFieldDto f : formFields) {
            if(f.getFieldId().equals("noteId")) {
                comment = f.getFieldValue();
            } else if(f.getFieldId().equals("usernameId")) {
                user = userRepository.findUserByUsername(f.getFieldValue());
            }
        }
        Long id = Long.parseLong((String) delegateExecution.getVariable("plagiarismComplaintId"));
        Optional<PlagiarismComplaint> plagiarismComplaint = plagiarismComplaintRepository.findById(id);
        for(PlagiarismComplaintResponse response : plagiarismComplaint.get().getResponses()) {
            if(response.getEditor().getId().equals(user.getId())) {
                Optional<PlagiarismComplaintResponse> ret = plagiarismComplaintResponseRepository.findById(response.getId());
                ret.get().setComment(comment);
                plagiarismComplaintResponseRepository.save(ret.get());
            }
        }

        plagiarismComplaintRepository.save(plagiarismComplaint.get());

    }
}
