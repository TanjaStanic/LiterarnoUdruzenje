package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Book;
import upp.la.model.plagiarism.PlagiarismComplaint;

import java.util.Optional;

@Repository
public interface PlagiarismComplaintRepository
    extends JpaRepository<upp.la.model.plagiarism.PlagiarismComplaint, Long> {

    Optional<PlagiarismComplaint> findByWritersBook(Book book);
}
