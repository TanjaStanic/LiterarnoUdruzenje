package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upp.la.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findDocumentByFileUrl(String FileUrl);
}
