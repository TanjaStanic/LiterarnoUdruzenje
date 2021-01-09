package com.example.bitcoinms.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.util.Assert;

import com.example.bitcoinms.utils.AttributeEncryptor;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //TODO Remove this
    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String merchantID; // sifrovati

    //TODO Remove this
    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String merchantPassword; //sifrovati

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private Long pcClientId;

    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String token;

    public Client(Long id, String merchantID, String merchantPassword, String email, String name) {
        Assert.notNull(id, "Attribute id cannot be null.");
        this.id = id;
        init(email, name, merchantID, merchantPassword);

    }
    public Client( String merchantID, String merchantPassword, String email, String name) {
        init(email, name, merchantID, merchantPassword);

    }
    private void init(String email, String name, String merchantID, String merchantPassword){
        Assert.notNull(email, "Attribute email cannot be null.");
        Assert.notNull(name, "Attribute name cannot be null.");
        Assert.notNull(merchantID, "Attribute merchantID cannot be null.");
        Assert.notNull(merchantPassword, "Attribute merchantPassword cannot be null.");
        this.merchantID = merchantID;
        this.merchantPassword = merchantPassword;
        this.email = email;
        this.name = name;
    }

}
