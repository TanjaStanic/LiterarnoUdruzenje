package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.registration.RegistrationApplicationResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Repository
public interface RegistrationApplicationResponseRepository
    extends JpaRepository<RegistrationApplicationResponse, Long> {
    ArrayList<RegistrationApplicationResponse> findAllByLecturerId(Long id);
    ArrayList<RegistrationApplicationResponse> findAll();
}
