package upp.la.service.publishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upp.la.dto.FormFieldDto;
import upp.la.model.Book;
import upp.la.model.Genre;
import upp.la.model.Role;
import upp.la.model.User;
import upp.la.repository.BookRepository;
import upp.la.repository.GenreRepository;
import upp.la.repository.UserRepository;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

@Service
@Component
public class SelectEditor implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormFieldDto> dtos = (List<FormFieldDto>) delegateExecution.getVariable("Book details form");
        List<User> editors = userRepository.findUsersByRole(Role.EDITOR);
        Book book = new Book();
        User user = new User();
        Genre genre = new Genre();
        for(FormFieldDto f : dtos) {
            if(f.getFieldId().equals("workingTitleId")) {
                book.setTitle(f.getFieldValue());
            } else if(f.getFieldId().equals("genresListId")) {
                genre = genreRepository.findGenreById(Long.parseLong(f.getFieldValue()));
                System.out.println("Id zanra za dodavanje knjige je " + genre.getId().toString());
            } else if(f.getFieldId().equals("synopsisId")) {
                book.setSynopsis(f.getFieldValue());
            } else if(f.getFieldId().equals("writerUsernameId")) {
                user = userRepository.findUserByUsername(f.getFieldValue());
                ArrayList<User> writer = new ArrayList<>();
                writer.add(user);
                book.setWriters(writer);
            }
        }
        Random rand = new Random();
        int randomNumber = rand.nextInt(editors.size());
        System.out.println("Random broj je " + randomNumber);
        User editor = editors.get(randomNumber);
        book.setEditor(editor);
        bookRepository.save(book);
        genre.getBooks().add(book);
        genreRepository.save(genre);
        user.getBooks().add(book);
        userRepository.save(user);

        //Dodati email koji se salje editoru
    }
}
