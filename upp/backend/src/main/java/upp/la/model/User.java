package upp.la.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

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
    private Role role;
    
    @Column
    private Boolean activated;
    
    public User(){
    	activated=false;
    }

    
}
