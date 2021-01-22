package upp.la.model.registration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.Document;
import upp.la.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationApplication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //Writer trying to apply
  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User writer;

  @Temporal(TemporalType.DATE)
  private Date createdDate;

  @Enumerated(EnumType.STRING)
  private ApplicationResponse finalResponse;

  //Documents the writer has uploaded
  @OneToMany(mappedBy = "registrationApplication", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Document> documents = new ArrayList<>();

  //Responses containing lecturer comments
  @JsonIgnore
  @OneToMany(mappedBy = "registrationApplication", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RegistrationApplicationResponse> responses = new ArrayList<>();

  public RegistrationApplication(User user) {
    this.writer = user;
    createdDate = new Date();
  }
}
