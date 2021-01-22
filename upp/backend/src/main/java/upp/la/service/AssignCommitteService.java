package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class AssignCommitteService implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationApplicationRepository registrationApplicationRepository;

    @Autowired
    private RegistrationApplicationResponseRepository registrationApplicationResponseRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("AssingCommiteService");
        List<FormFieldDto> dtos =
                (List<FormFieldDto>) delegateExecution.getVariable("files");

        User user = userRepository.findUserByUsername(dtos.get(1).getFieldValue());
        ArrayList<RegistrationApplication> ras = registrationApplicationRepository.findRegistrationApplicationByWriterIdOrderByCreatedDateDesc(user.getId());
        RegistrationApplication ra = ras.get(0);
        List<User> allLectures = userRepository.findUsersByRole(Role.LECTURER);
        int i = 0;
        //Za sad je ovako napravljeno dok ne smislim random funkciju :D (uvek cu uzeti prva 3 odnosno ona ubacena u tabelu)
        for(User u : allLectures) {
            if(i < 3) {
                RegistrationApplicationResponse rap = new RegistrationApplicationResponse();
                rap.setLecturer(u);
                rap.setRegistrationApplication(ra);
                i++;
                registrationApplicationResponseRepository.save(rap);
            }
        }

        delegateExecution.setVariable("COUNTER", 1);

    }
}
