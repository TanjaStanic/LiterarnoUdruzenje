package upp.la.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.exceptions.EntityNotFound;
import upp.la.model.ConfirmationToken;
import upp.la.model.EmailTemplate;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.ConfirmationTokenRepository;
import upp.la.repository.RoleRepository;
import upp.la.repository.UserRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceInt {
  @Autowired private Environment env;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Lazy @Autowired PasswordEncoder passwordEncoder;

  @Autowired ConfirmationTokenRepository confirmationTokenRepository;

  public User registerNew(User user) throws IOException {
    Set<Role> roles = new HashSet<Role>();

    roles.add(roleRepository.findByName("ADMIN"));

    user.setRoles(roles);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User saved = userRepository.save(user);

    ConfirmationToken confirmationToken = new ConfirmationToken(saved);

    confirmationTokenRepository.save(confirmationToken);

    EmailTemplate email =
        new EmailTemplate(
            user.getEmail(),
            EmailTemplate.VERIFY_SUBJECT(),
            EmailTemplate.VERIFY_MESSAGE(
                env.getProperty("app.registration-url")
                    + confirmationToken.getConfirmationToken()));

    // Requests.sendEmail(email);

    return saved;
  }

  public void verifyAccount(String confirmationToken) throws EntityNotFound {
    ConfirmationToken token =
        confirmationTokenRepository.findByConfirmationToken(confirmationToken);

    if (token == null) {
      throw new EntityNotFound(ErrorMessages.TOKEN_ERROR());
    }

    User user = userRepository.findByEmail(token.getUser().getEmail());

    if (user == null) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    user.setActivated(true);

    userRepository.save(user);
    confirmationTokenRepository.delete(token);
  }
}
