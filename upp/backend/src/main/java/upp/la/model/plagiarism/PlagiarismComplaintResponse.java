package upp.la.model.plagiarism;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.User;
import upp.la.model.registration.RegistrationApplication;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlagiarismComplaintResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //The complaint the response is related to
    @ManyToOne(fetch = FetchType.EAGER)
    private PlagiarismComplaint plagiarismComplaint;

    //Editor making the response
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User editor;

    @Column
    private Boolean plagiarisedResponse;

    @Column private String comment;

    public PlagiarismComplaintResponse(
        PlagiarismComplaint complaint, User editor) {
        this.plagiarismComplaint = complaint;
        this.editor = editor;
    }
}
