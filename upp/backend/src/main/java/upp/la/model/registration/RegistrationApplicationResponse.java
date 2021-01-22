package upp.la.model.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationApplicationResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //The application the response is related to
  @ManyToOne(fetch = FetchType.EAGER)
  private RegistrationApplication registrationApplication;

  //Lecturer making the response
  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User lecturer;

  @Enumerated(EnumType.STRING)
  private ApplicationResponse response;

  @Column private String comment;

  public RegistrationApplicationResponse(
      RegistrationApplication registrationApplication, User lecturer) {
    this.registrationApplication = registrationApplication;
    this.lecturer = lecturer;
  }
}
