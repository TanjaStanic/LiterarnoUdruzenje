package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByEmail(String email);
  List<User> findAll();
  User findUserByEmail(String email);
  User findUserByUsername(String username);
  User findByUsername(String username);
}
