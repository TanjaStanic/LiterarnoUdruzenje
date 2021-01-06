package com.example.bankms.domain;

import com.example.bankms.utils.AttributeEncryptor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.*;

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
    private Long pcClientId;

    public Client(String email, String merchantID, String merchantPassword, long pcClientId) {
        init(email, merchantID, merchantPassword, pcClientId);
    }

    private void init(String email, String merchantID, String merchantPassword, Long pcClientId){
        Assert.notNull(email, "Attribute email cannot be null.");
        Assert.notNull(merchantID, "Attribute merchantID cannot be null.");
        Assert.notNull(merchantPassword, "Attribute merchantPassword cannot be null.");
        Assert.notNull(pcClientId, "Attribute pcClientId cannot be null.");
        this.merchantID = merchantID;
        this.merchantPassword = merchantPassword;
        this.email = email;
        this.pcClientId = pcClientId;

    }
}
