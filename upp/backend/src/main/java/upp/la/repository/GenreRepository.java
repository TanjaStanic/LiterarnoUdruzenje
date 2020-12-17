package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upp.la.model.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAll();

}
