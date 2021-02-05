package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Book;
import upp.la.model.User;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    ArrayList<Book> findAll();
    Book findBookByTitle(String title);
    ArrayList<Book> findAllByEditor(User editor);
    ArrayList<Book> findAllByLecturer(User lecturer);

    Optional<Book> findByTitle(String title);
}
