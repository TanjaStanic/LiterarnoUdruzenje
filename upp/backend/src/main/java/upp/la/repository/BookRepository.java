package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
