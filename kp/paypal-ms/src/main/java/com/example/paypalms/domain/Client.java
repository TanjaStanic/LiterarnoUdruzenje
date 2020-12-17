package com.example.paypalms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String clientId;    //sifrovati

    @Column
    private String clientSecret;    //sifrovati

    public Client(Long id, String email, String clientId, String clientSecret) {
        init(email, clientId, clientSecret);
        Assert.notNull(id, "Attribute id cannot be null.");
        this.id = id;
    }

    public Client(String email, String clientId, String clientSecret) {
       init(email, clientId, clientSecret);
    }

    private void init(String email, String clientId, String clientSecret){
        Assert.notNull(email, "Attribute email cannot be null.");
        Assert.notNull(email, "Attribute clientId cannot be null.");
        Assert.notNull(email, "Attribute clientSecret cannot be null.");
        this.email = email;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
