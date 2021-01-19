package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import upp.la.model.auth.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	ConfirmationToken findOneByToken(String token);
	ConfirmationToken findOneById(long id);
}
