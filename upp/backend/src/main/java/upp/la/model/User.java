package upp.la.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String username;

  @Column private String password;

  @Column private String firstName;

  @Column private String lastName;

  @Column private String email;

  @Column private String city;

  @Column private String country;

  @Column private Boolean activated;

  // Genres readers are interested in
  @ManyToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY)
  @JoinTable(
      name = "reader_genres",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")})
  private Collection<Genre> genres;

  // Genres beta-readers are interested in
  @ManyToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY)
  @JoinTable(
      name = "beta_genres",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")})
  private Collection<Genre> betaGenres;

  // Books writers have contributed to/authored
    @JsonIgnore
  @ManyToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY)
  @JoinTable(
      name = "writers_books",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
  private Collection<Book> books;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User() {
    activated = false;
  }
}
