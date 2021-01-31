package upp.la.listener.plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertUsersOnCreate implements JavaDelegate {

  @Autowired IdentityService identityService;

  public void execute(DelegateExecution execution) throws Exception {
    User user1 = identityService.newUser("editor1");
    user1.setFirstName("John");
    user1.setLastName("Doe");
    user1.setPassword("123456");
    user1.setEmail("john@camunda.org");
    identityService.saveUser(user1);

    User user2 = identityService.newUser("editor2");
    user2.setFirstName("Mary");
    user2.setLastName("Anne");
    user2.setPassword("123456");
    user2.setEmail("mary@camunda.org");
    identityService.saveUser(user2);

    User user3 = identityService.newUser("editor3");
    user3.setFirstName("Peter");
    user3.setLastName("Meter");
    user3.setPassword("123456");
    user3.setEmail("peter@camunda.org");
    identityService.saveUser(user3);

    Group lecturers = identityService.newGroup("editors");
    lecturers.setName("Editors Committee");
    lecturers.setType("WORKFLOW");
    identityService.saveGroup(lecturers);

    identityService.createMembership("demo", "editors");
    identityService.createMembership("editor1", "editors");
    identityService.createMembership("editor2", "editors");
    identityService.createMembership("editor3", "editors");
  }
}
