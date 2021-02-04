package upp.la.listener;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Component;

@Component
public class InsertUsersOnCreate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    ProcessEngine engine = execution.getProcessEngine();

    IdentityService identityService = engine.getIdentityService();

    if (identityService.isReadOnly()) {
      System.out.println("Identity service provider is Read Only, not creating any users.");
      return;
    }

    Group group1 = identityService.createGroupQuery().groupId("editors").singleResult();

    User user = identityService.newUser("writer");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setPassword("123456");
    user.setEmail("john@camunda.org");
    identityService.saveUser(user);

    if (group1 == null) {
     User user0 = identityService.newUser("editor0");
     user0.setFirstName("Angela");
     user0.setLastName("Doe");
     user0.setPassword("123456");
     user0.setEmail("email@editor0.org");
     identityService.saveUser(user0);
    	
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

      Group editors = identityService.newGroup("editors");
      editors.setName("Editors Committee");
      editors.setType("WORKFLOW");
      identityService.saveGroup(editors);

      identityService.createMembership("demo", "editors");
      identityService.createMembership("editor1", "editors");
      identityService.createMembership("editor2", "editors");
      identityService.createMembership("editor3", "editors");
    }

    Group group2 = identityService.createGroupQuery().groupId("betaReaders").singleResult();

    if (group2 == null) {
      User user4 = identityService.newUser("betaReader1");
      user4.setFirstName("John");
      user4.setLastName("Doe");
      user4.setPassword("123456");
      user4.setEmail("john@camunda.org");
      identityService.saveUser(user4);

      User user5 = identityService.newUser("betaReader2");
      user5.setFirstName("Mary");
      user5.setLastName("Anne");
      user5.setPassword("123456");
      user5.setEmail("mary@camunda.org");
      identityService.saveUser(user5);

      User user6 = identityService.newUser("betaReader3");
      user6.setFirstName("Peter");
      user6.setLastName("Meter");
      user6.setPassword("123456");
      user6.setEmail("peter@camunda.org");
      identityService.saveUser(user6);

      Group betaReaders = identityService.newGroup("betaReaders");
      betaReaders.setName("Beta Readers");
      betaReaders.setType("WORKFLOW");
      identityService.saveGroup(betaReaders);

      identityService.createMembership("demo", "betaReaders");
      identityService.createMembership("betaReader1", "betaReaders");
      identityService.createMembership("betaReader2", "betaReaders");
      identityService.createMembership("betaReader3", "betaReaders");
    }

    Group group3 = identityService.createGroupQuery().groupId("lecturers").singleResult();

    if (group3 == null) {
      User user7 = identityService.newUser("lecturer1");
      user7.setFirstName("John");
      user7.setLastName("Doe");
      user7.setPassword("123456");
      user7.setEmail("john@camunda.org");
      identityService.saveUser(user7);

      User user8 = identityService.newUser("lecturer2");
      user8.setFirstName("Mary");
      user8.setLastName("Anne");
      user8.setPassword("123456");
      user8.setEmail("mary@camunda.org");
      identityService.saveUser(user8);

      User user9 = identityService.newUser("lecturer3");
      user9.setFirstName("Peter");
      user9.setLastName("Meter");
      user9.setPassword("123456");
      user9.setEmail("peter@camunda.org");
      identityService.saveUser(user9);

      Group lecturers = identityService.newGroup("lecturers");
      lecturers.setName("Lecturer Committee");
      lecturers.setType("WORKFLOW");
      identityService.saveGroup(lecturers);

      identityService.createMembership("demo", "lecturers");
      identityService.createMembership("lecturer1", "lecturers");
      identityService.createMembership("lecturer2", "lecturers");
      identityService.createMembership("lecturer3", "lecturers");
    }
  }
}
