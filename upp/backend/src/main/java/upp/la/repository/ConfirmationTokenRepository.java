package upp.la.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.auth.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
  ConfirmationToken findByConfirmationToken(String confirmationToken);
}
