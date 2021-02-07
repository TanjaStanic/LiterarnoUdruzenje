package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.repository.BookRepository;

import java.util.List;

@Service
@Component
public class InitialBookReviewService implements JavaDelegate {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> fields = (List<FormFieldDto>) delegateExecution.getVariable("Initial book review");
        List<FormFieldDto> tmp = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        Book book = new Book();
        for(FormFieldDto f1 : tmp) {
            if(f1.getFieldId().equals("workingTitleId")) {
                book = bookRepository.findBookByTitle(f1.getFieldValue());
            }
        }
        for(FormFieldDto f : fields) {
            if(f.getFieldId().equals("ReadManuscriptId")) {
                if(f.getFieldValue().equals("Da")) {
                    delegateExecution.setVariable("flag", true);
                    book.setAccepted(true);
                    bookRepository.save(book);
                } else {
                    delegateExecution.setVariable("flag", false);
                    book.setAccepted(false);
                    bookRepository.save(book);
                }
            }
        }
    }
}
