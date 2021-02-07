package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.BookComments;
import upp.la.model.EmailTemplate;
import upp.la.model.User;
import upp.la.repository.BookCommentsRepo;
import upp.la.repository.BookRepository;
import upp.la.repository.UserRepository;
import upp.la.util.Requests;

import java.util.List;


@Service
@Component
public class SendCommentsToWriter implements JavaDelegate {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookCommentsRepo bookCommentsRepo;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //Mozda neki mail da se posalje piscu sa svim komentarima za knjigu
        List<FormFieldDto> tmp = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        Book book = new Book();
        User user = new User();
        for(FormFieldDto f : tmp) {
            if(f.getFieldId().equals("workingTitleId")) {
                book = bookRepository.findBookByTitle(f.getFieldValue());
            } else if(f.getFieldId().equals("writerUsernameId")) {
                user = userRepository.findUserByUsername(f.getFieldValue());
            }
        }

        List<BookComments> bookComments = book.getComments();

        EmailTemplate
            email = EmailTemplate.PublishingBetaCommentsWriter(bookComments);

        email.setAddress(user.getEmail());

        //Requests.sendEmail(email);
    }
}
