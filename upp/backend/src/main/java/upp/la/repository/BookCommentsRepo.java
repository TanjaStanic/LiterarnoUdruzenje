package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.BookComments;

@Repository
public interface BookCommentsRepo extends JpaRepository<BookComments, Long> {
}
