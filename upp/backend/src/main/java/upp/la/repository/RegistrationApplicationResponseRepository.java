package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;
import upp.la.model.registration.RegistrationApplicationResponse;

@Repository
public interface RegistrationApplicationResponseRepository
    extends JpaRepository<RegistrationApplicationResponse, Long> {
	
	RegistrationApplicationResponse findOneById(Long id);
	RegistrationApplicationResponse findOneByLecturer(User lecturer);
	RegistrationApplicationResponse findOneByRegistrationApplication(RegistrationApplication registrationApplication);
	ArrayList<RegistrationApplicationResponse> findAllByLecturerId(Long id);
    ArrayList<RegistrationApplicationResponse> findAll();

}
