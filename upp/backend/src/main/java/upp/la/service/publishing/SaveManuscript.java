package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.Document;
import upp.la.repository.BookRepository;
import upp.la.repository.DocumentRepository;

import java.util.List;

@Service
@Component
public class SaveManuscript implements JavaDelegate {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> fields = (List<FormFieldDto>) delegateExecution.getVariable("Submit manuscript");
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        Document d = new Document();
        Book book = new Book();
        for(FormFieldDto f: fields) {
            if(f.getFieldId().equals("ManuscriptLinkId")) {
                d = documentRepository.findDocumentByFileUrl("http://localhost:8080/files/download/" + f.getFieldValue());
            }
        }

        for(FormFieldDto f1: dtos) {
            if(f1.getFieldValue().equals("workingTitleId")) {
                book = bookRepository.findBookByTitle(f1.getFieldValue());
            }
        }
        book.setDocument(d);
        d.setBook(book);
        documentRepository.save(d);
        bookRepository.save(book);

        delegateExecution.setVariable("plagiarism", false);
    }
}
