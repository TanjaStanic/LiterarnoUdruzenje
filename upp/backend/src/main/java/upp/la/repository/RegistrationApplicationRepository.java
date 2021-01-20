package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;

public interface RegistrationApplicationRepository
    extends JpaRepository<RegistrationApplication, Long> {
	
	RegistrationApplication findOneByWriter(User writer);
}
