package com.example.paypalms.domain;

import com.example.paypalms.utils.AttributeEncryptor;
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
    @Convert(converter = AttributeEncryptor.class)
    private String clientId;

    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String clientSecret;

    @Column
    private Long pcClientId;

    public Client(Long id, String email, String clientId, String clientSecret, Long pcClientId) {
        Assert.notNull(id, "Attribute id cannot be null.");
        this.id = id;
        init(email, clientId, clientSecret, pcClientId);
    }

    public Client(String email, String clientId, String clientSecret, Long pcClientId) {
        init(email, clientId, clientSecret, pcClientId);
    }

    private void init(String email, String clientId, String clientSecret, Long pcClientId) {
        Assert.notNull(email, "Attribute email cannot be null.");
        Assert.notNull(email, "Attribute clientId cannot be null.");
        Assert.notNull(email, "Attribute clientSecret cannot be null.");
        Assert.notNull(email, "Attribute pcClientId cannot be null.");
        this.email = email;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.pcClientId = pcClientId;
    }

}
