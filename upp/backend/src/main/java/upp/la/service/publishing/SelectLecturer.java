package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.BookRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Component
public class SelectLecturer implements JavaDelegate {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        ArrayList<User> lectures = (ArrayList<User>) userRepository.findUsersByRole(Role.LECTURER);
        Book book = new Book();

        for(FormFieldDto f1: dtos) {
            if(f1.getFieldId().equals("workingTitleId")) {
                book = bookRepository.findBookByTitle(f1.getFieldValue());
            }
        }
        Random rand = new Random();
        int randomNumber = rand.nextInt(lectures.size());
        User user = lectures.get(randomNumber);
        book.setLecturer(user);
        bookRepository.save(book);
    }
}
