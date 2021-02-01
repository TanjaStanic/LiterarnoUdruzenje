package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.BookComments;
import upp.la.model.User;
import upp.la.repository.BookCommentsRepo;
import upp.la.repository.BookRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class SaveBetaReaders implements JavaDelegate {

    @Autowired
    BookCommentsRepo bookCommentsRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Select beta readers");
        List<FormFieldDto> tmp = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");

        String title = tmp.get(0).getFieldValue();
        Book book = bookRepository.findBookByTitle(title);
        ArrayList<BookComments> comments = new ArrayList<>();

        for (FormFieldDto f: dtos) {
            User user = userRepository.findUserByUsername(f.getFieldValue());
            BookComments b = new BookComments();
            b.setBetaReader(user);
            bookCommentsRepo.save(b);
            comments.add(b);
        }

        book.setComments(comments);
        bookRepository.save(book);
    }
}
