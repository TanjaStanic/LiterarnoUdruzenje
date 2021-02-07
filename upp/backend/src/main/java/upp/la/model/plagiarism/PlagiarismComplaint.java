package upp.la.model.plagiarism;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlagiarismComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Writers book
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "writers_book_id")
    private Book writersBook;

    //Plagiarised book
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "plagiarised_book_id")
    private Book plagiarisedBook;

    @Column
    private Boolean finalResponse;

    //Responses containing editors comments
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "complaint_comments",
        joinColumns = {@JoinColumn(name = "plagiarism_complaint_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "plagiarism_complaint_response_id", referencedColumnName = "id",  nullable=true)})
    private List<PlagiarismComplaintResponse> responses = new ArrayList<>();
}
