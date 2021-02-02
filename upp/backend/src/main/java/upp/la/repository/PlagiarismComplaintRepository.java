package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlagiarismComplaintRepository
    extends JpaRepository<upp.la.model.plagiarism.PlagiarismComplaint, Long> {

}
