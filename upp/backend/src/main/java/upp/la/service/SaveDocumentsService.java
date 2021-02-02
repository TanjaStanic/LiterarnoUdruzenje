package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
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

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class SaveDocumentsService implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RegistrationApplicationRepository registrationApplicationRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> files =
                (List<FormFieldDto>) delegateExecution.getVariable("files");
        int num = 0;
        User user = userRepository.findUserByUsername(files.get(1).getFieldValue());
        String[] parts = files.get(0).getFieldValue().split(",");
        RegistrationApplication ra = new RegistrationApplication();
        ArrayList<Document> documents = new ArrayList<Document>();
        for(String s : parts) {
            Document d = documentRepository.findDocumentByFileUrl("http://localhost:8080/files/download/" + s);
            documents.add(d);
        }
        ra.setDocuments(documents);
        ra.setWriter(user);
        LocalDateTime localDateTime = LocalDateTime.now();
        ra.setCreatedDate(localDateTime.toDate());
        ra = registrationApplicationRepository.save(ra);
        Document d;
        for(String s : parts) {
            d = documentRepository.findDocumentByFileUrl("http://localhost:8080/files/download/" + s);
            d.setRegistrationApplication(ra);
            documentRepository.save(d);
            num++;
        }
        delegateExecution.setVariable("docNumber", num);
    }
}
