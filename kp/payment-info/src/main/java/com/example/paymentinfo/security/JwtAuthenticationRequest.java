package com.example.paymentinfo.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
public class JwtAuthenticationRequest {
    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        Assert.notNull(username, "Attribute username cannot be null.");
        Assert.notNull(password, "Attribute password cannot be null.");
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        Assert.notNull(username, "Attribute username cannot be null.");
        this.username = username;
    }

    public void setPassword(String password) {
        Assert.notNull(password, "Attribute password cannot be null.");
        this.password = password;
    }
}
