package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByEmail(String email);
  List<User> findAll();
  User findUserByEmail(String email);
  User findUserByUsername(String username);
  User findByUsername(String username);

  User findOneByRegistrationApplication(RegistrationApplication registrationApplication);

  List<User> findUsersByRole(Role role);
}
