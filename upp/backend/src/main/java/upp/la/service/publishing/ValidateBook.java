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

@Service
@Component
public class ValidateBook implements JavaDelegate {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        boolean validated;
        // Book title
        String title = dtos.get(0).getFieldValue();
        Book book = bookRepository.findBookByTitle(title);
        if(book == null) {
            delegateExecution.setVariable("validated", true);
        }else {
            delegateExecution.setVariable("validated", false);
        }
    }
}
