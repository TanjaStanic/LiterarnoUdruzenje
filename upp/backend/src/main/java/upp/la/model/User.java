package upp.la.model;

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

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private Boolean activated;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(
	        name = "User_Genres",
	        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
	        inverseJoinColumns = { @JoinColumn(name = "genre_id", referencedColumnName = "id") }
	  )
    private Collection<Genre> genres;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){
    	activated=false;
    }
}
