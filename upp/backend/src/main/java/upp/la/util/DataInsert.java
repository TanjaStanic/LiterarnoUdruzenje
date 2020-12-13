package upp.la.util;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;

public class DataInsert {

    public void users(IdentityService identityService) {
        User user2 = identityService.newUser("john");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setPassword("john");
        user2.setEmail("john@camunda.org");
        identityService.saveUser(user2);

        User user3 = identityService.newUser("mary");
        user3.setFirstName("Mary");
        user3.setLastName("Anne");
        user3.setPassword("mary");
        user3.setEmail("mary@camunda.org");
        identityService.saveUser(user3);

        User user4 = identityService.newUser("peter");
        user4.setFirstName("Peter");
        user4.setLastName("Meter");
        user4.setPassword("peter");
        user4.setEmail("peter@camunda.org");
        identityService.saveUser(user4);
    }
}
