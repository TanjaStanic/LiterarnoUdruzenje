package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import upp.la.dto.FormFieldDto;
import upp.la.model.Document;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;
import upp.la.repository.DocumentRepository;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class MoreDocumentsService implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RegistrationApplicationRepository registrationApplicationRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("USAO SAM U MORE DOCUMENT SERVICE");
        List<FormFieldDto> files =
                (List<FormFieldDto>) delegateExecution.getVariable("files");

        User user = userRepository.findUserByUsername(files.get(1).getFieldValue());
        String[] parts = files.get(0).getFieldValue().split(",");
        RegistrationApplication ra = registrationApplicationRepository.findOneByWriter(user);
        for(String s : parts) {
            Document d = documentRepository.findDocumentByFileUrl("http://localhost:8080/files/download/" + s);
            d.setRegistrationApplication(ra);
            documentRepository.save(d);
            ra.getDocuments().add(d);
        }

        registrationApplicationRepository.save(ra);
    }
}
