package upp.la.util;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInsert implements JavaDelegate {

    @Autowired IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        User user1 = identityService.newUser("lecturer1");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("123456");
        user1.setEmail("john@camunda.org");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("lecturer2");
        user2.setFirstName("Mary");
        user2.setLastName("Anne");
        user2.setPassword("123456");
        user2.setEmail("mary@camunda.org");
        identityService.saveUser(user2);

        User user3 = identityService.newUser("lecturer3");
        user3.setFirstName("Peter");
        user3.setLastName("Meter");
        user3.setPassword("123456");
        user3.setEmail("peter@camunda.org");
        identityService.saveUser(user3);

        Group lecturers = identityService.newGroup("lecturers");
        lecturers.setName("Lecturers Committee");
        lecturers.setType("WORKFLOW");
        identityService.saveGroup(lecturers);

        identityService.createMembership("demo", "lecturers");
        identityService.createMembership("lecturer1", "lecturers");
        identityService.createMembership("lecturer2", "lecturers");
        identityService.createMembership("lecturer3", "lecturers");
    }
}
