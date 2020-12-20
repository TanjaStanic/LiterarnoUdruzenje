package upp.la.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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
    
    public User(){
    	activated=false;
    }

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    public User() {
        this.activated = false;
    }
}
