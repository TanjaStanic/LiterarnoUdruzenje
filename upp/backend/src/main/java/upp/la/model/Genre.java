package upp.la.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

  @ManyToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "genre_books",
      joinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
  private Collection<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
