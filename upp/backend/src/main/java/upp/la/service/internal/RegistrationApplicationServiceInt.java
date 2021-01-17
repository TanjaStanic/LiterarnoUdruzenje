package upp.la.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.model.Document;
import upp.la.model.User;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.model.registration.ApplicationResponse;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;
import upp.la.repository.DocumentRepository;
import upp.la.repository.RegistrationApplicationRepository;
import upp.la.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationApplicationServiceInt {

  @Autowired UserRepository userRepository;

  @Autowired RegistrationApplicationRepository applicationRepository;

  @Autowired DocumentRepository documentRepository;

  public RegistrationApplication createNew(Long writerId) throws EntityNotFound {

    Optional<User> writer = userRepository.findById(writerId);

    if (!writer.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    RegistrationApplication regApp = new RegistrationApplication(writer.get());

    return applicationRepository.save(regApp);
  }

  public RegistrationApplication addDocument(Long registrationApplicationId, Long documentId)
      throws EntityNotFound {

    Optional<RegistrationApplication> regApp =
        applicationRepository.findById(registrationApplicationId);

    if (!regApp.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    Optional<Document> document = documentRepository.findById(documentId);

    if (!document.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    regApp.get().getDocuments().add(document.get());

    return applicationRepository.save(regApp.get());
  }

  public List<RegistrationApplicationResponse> getResponses(Long registrationApplicationId)
      throws EntityNotFound {
    Optional<RegistrationApplication> regApp =
        applicationRepository.findById(registrationApplicationId);

    if (!regApp.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    return regApp.get().getResponses();
  }

  public List<Document> getDocuments(Long registrationApplicationId) throws EntityNotFound {
    Optional<RegistrationApplication> regApp =
        applicationRepository.findById(registrationApplicationId);

    if (!regApp.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    return regApp.get().getDocuments();
  }

  public RegistrationApplication setFinalResponse(
      Long registrationApplicationId, ApplicationResponse applicationResponse)
      throws EntityNotFound {
    Optional<RegistrationApplication> regApp =
        applicationRepository.findById(registrationApplicationId);

    if (!regApp.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    regApp.get().setFinalResponse(applicationResponse);

    return applicationRepository.save(regApp.get());
  }

  public void delete(Long registrationApplicationId) throws EntityNotFound {
    if (applicationRepository.existsById(registrationApplicationId)) {
      applicationRepository.deleteById(registrationApplicationId);
    } else {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }
  }
}
