package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Card;
import upp.la.model.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

	Card findOneByOwner(User owner);
}
