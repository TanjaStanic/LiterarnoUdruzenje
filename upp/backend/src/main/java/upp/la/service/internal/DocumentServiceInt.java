package upp.la.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.model.Document;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.repository.DocumentRepository;

@Service
public class DocumentServiceInt {

  @Autowired DocumentRepository documentRepository;

  public Document createNew(String fileUrl) {
    Document document = new Document(fileUrl);

    return documentRepository.save(document);
  }

  public void delete(Long documentId) throws EntityNotFound {
    if (documentRepository.existsById(documentId)) {
      documentRepository.deleteById(documentId);
    } else {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }
  }
}
