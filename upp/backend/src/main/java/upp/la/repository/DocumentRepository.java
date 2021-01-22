package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findDocumentByFileUrl(String FileUrl);
}
