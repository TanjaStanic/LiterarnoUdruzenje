package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.repository.BookRepository;

import java.util.List;
import java.util.UUID;

@Service
@Component
public class IndexAndPrint implements JavaDelegate {

  @Autowired BookRepository bookRepository;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    List<FormFieldDto> dtos =
        (List<FormFieldDto>) delegateExecution.getVariable("Book details form");

    Book book = new Book();
    for (FormFieldDto f1 : dtos) {
      if (f1.getFieldId().equals("workingTitleId")) {
        book = bookRepository.findBookByTitle(f1.getFieldValue());
      }
    }

    String isbn = UUID.randomUUID().toString();

    book.setIsbn(isbn);

    bookRepository.save(book);

    System.out.println("Indexing book with isbn: " + isbn);

    System.out.println("Printing book...");
  }
}
