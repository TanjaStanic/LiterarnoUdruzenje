package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.UserRepository;

import java.text.Normalizer;
import java.util.List;

@Service
@Component
public class BetaReaderPenalty implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Beta reader comments");
        User user = new User();
        for(FormFieldDto f : dtos) {
            if(f.getFieldId().equals("usernameId")) {
                user = userRepository.findUserByUsername(f.getFieldValue());
            }
        }
        int points = user.getPoints();
        points++;
        user.setPoints(points);

        if(user.getPoints() >= 5) {
            user.setRole(Role.READER);
            //Obavestiti usera mailom
        }

        userRepository.save(user);
    }
}
