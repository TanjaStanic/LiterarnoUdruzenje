package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upp.la.model.registration.RegistrationApplication;

import java.util.ArrayList;

public interface RegistrationApplicationRepository
    extends JpaRepository<RegistrationApplication, Long> {
    RegistrationApplication findRegistrationApplicationById(Long id);
    ArrayList<RegistrationApplication> findRegistrationApplicationByWriterIdOrderByCreatedDateDesc(Long id);
}

