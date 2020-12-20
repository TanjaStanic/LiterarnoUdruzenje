package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import upp.la.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

<<<<<<< HEAD
	boolean existsByUserName(String userName);
=======
    User findByEmail(String email);

    User findByUsername(String username);
>>>>>>> 65b616a... Add email verification, setup security, other
}
