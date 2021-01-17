package upp.la.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String title;

  @Column(unique = true)
  private String isbn;

  //List of books writers
  @ManyToMany(mappedBy = "books")
  private List<User> writers = new ArrayList<>();

  @Column private String keyTerms;

  @Column private String publisher;

  @Column private String yearPublished;

  @Column private String placePublished;

  @Column private Integer pages;

  @Column private String synopsis;

  //Document object containing the path to the file
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "document_id", referencedColumnName = "id")
  private Document document;

  public Book(Document document) {
    this.document = document;
  }
}
