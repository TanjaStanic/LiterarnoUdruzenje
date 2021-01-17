package upp.la.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.RegistrationApplicationResponseRepository;
import upp.la.repository.UserRepository;

import java.util.Optional;

@Service
public class RegistrationApplicationResponseServiceInt {
  // TODO Expand update/creation with data from DTO

  @Autowired RegistrationApplicationResponseRepository responseRepository;

  @Autowired RegistrationApplicationRepository applicationRepository;

  @Autowired UserRepository userRepository;

  public void createNew(Long applicationId, Long lecturerId) throws EntityNotFound {
    Optional<RegistrationApplication> application = applicationRepository.findById(applicationId);

    if (!application.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    Optional<User> lecturer = userRepository.findById(lecturerId);

    if (!lecturer.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    RegistrationApplicationResponse response =
        new RegistrationApplicationResponse(application.get(), lecturer.get());

    application.get().getResponses().add(response);

    applicationRepository.save(application.get());
  }
}
