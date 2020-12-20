package upp.la.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/*public enum Role {
	READER, BETA_READER, WRITER, LECTURER, EDITOR, ADMIN
}*/

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable
  private Set<User> users;

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    } else if (name != null) {
      return name.hashCode();
    }

    return 0;
  }

  @Override
  public boolean equals(Object another) {
    if (!(another instanceof Role)) return false;

    Role anotherRole = (Role) another;

    return anotherRole.id != null && (anotherRole.id.equals(this.id));
  }

  @Override
  public String toString() {
    return name;
  }
}
