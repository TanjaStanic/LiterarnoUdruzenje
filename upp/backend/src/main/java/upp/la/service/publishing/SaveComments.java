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
public class SaveComments implements JavaDelegate {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCommentsRepo bookCommentsRepo;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> fields = (List<FormFieldDto>) delegateExecution.getVariable("Beta reader comments");
        List<FormFieldDto> tmp = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        String comment = "";
        User u = new User();
        for(FormFieldDto f : fields) {
            if(f.getFieldId().equals("commentId")) {
                comment = f.getFieldValue();
            } else if (f.getFieldId().equals("usernameId")) {
                u = userRepository.findUserByUsername(f.getFieldValue());
            }
        }

        Book book = bookRepository.findBookByTitle(tmp.get(0).getFieldValue());
        ArrayList<BookComments> bookCommentsArrayList = (ArrayList<BookComments>) book.getComments();
        for(BookComments bookComments : bookCommentsArrayList) {
            if(bookComments.getBetaReader().getUsername().equals(u.getUsername())) {
                bookComments.setComment(comment);
            }
        }

        book.setComments(bookCommentsArrayList);
        bookRepository.save(book);
    }
}
