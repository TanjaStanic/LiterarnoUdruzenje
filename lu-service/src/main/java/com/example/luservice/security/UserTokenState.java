package com.example.luservice.security;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private Long userId;

    public UserTokenState(String accessToken, Long expiresIn, Long userId) {
        Assert.notNull(accessToken, "Attribute token cannot be null");
        Assert.notNull(expiresIn, "Attribute expiresIn cannot be null");
        Assert.notNull(userId, "Attribute userId cannot be null");
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.userId = userId;
    }

}
