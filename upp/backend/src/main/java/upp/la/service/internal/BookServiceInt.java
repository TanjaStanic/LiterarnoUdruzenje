package upp.la.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.error.ErrorMessages;
import upp.la.model.Book;
import upp.la.model.Document;
import upp.la.model.exceptions.EntityNotFound;
import upp.la.repository.BookRepository;
import upp.la.repository.DocumentRepository;

import java.util.Optional;

@Service
public class BookServiceInt {
  //TODO Expand update/creation with data from DTO

  @Autowired BookRepository bookRepository;

  @Autowired DocumentRepository documentRepository;

  public Book createNewFromDocument(Long documentId) throws EntityNotFound {
    Optional<Document> document = documentRepository.findById(documentId);

    if (!document.isPresent()) {
      throw new EntityNotFound(ErrorMessages.ENTITY_NOT_FOUND());
    }

    Book book = new Book(document.get());

    return bookRepository.save(book);
  }

  public Book createNewFromFileUrl(String fileUrl) {
    Document document = new Document(fileUrl);

    documentRepository.save(document);

    Book book = new Book(document);

    return bookRepository.save(book);
  }


}
