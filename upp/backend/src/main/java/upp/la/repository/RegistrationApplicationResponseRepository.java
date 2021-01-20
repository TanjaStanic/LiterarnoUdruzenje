package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upp.la.model.User;
import upp.la.model.registration.RegistrationApplicationResponse;

public interface RegistrationApplicationResponseRepository
    extends JpaRepository<RegistrationApplicationResponse, Long> {
	
	RegistrationApplicationResponse findOneByLecturer(User lecturer);
}
