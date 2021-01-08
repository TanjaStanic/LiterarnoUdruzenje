package com.example.bitcoinms.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.example.bitcoinms.utils.AttributeEncryptor;

import lombok.Data;
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

    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String merchantID; // sifrovati

    @Column
    @Convert(converter = AttributeEncryptor.class)
    private String merchantPassword; //sifrovati

    @Column
    private String email;

    @Column
    private String name;

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
