package upp.la.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upp.la.model.registration.RegistrationApplication;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //Document can be related to a book
  @JsonIgnore
  @OneToOne(mappedBy = "document")
  private Book book;

  @Column private String fileUrl;

  //Document can be related to a registration application
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private RegistrationApplication registrationApplication;

  public Document(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public Document(Book book, String fileUrl) {
    this.book = book;
    this.fileUrl = fileUrl;
  }
}
