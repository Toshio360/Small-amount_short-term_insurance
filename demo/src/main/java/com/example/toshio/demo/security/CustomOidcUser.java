package com.example.toshio.demo.security;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.example.toshio.client.model.User;

public class CustomOidcUser extends DefaultOidcUser {

    private final User opUser;

    public CustomOidcUser(OidcUser oidcUser, User opUser) {
        super(
                oidcUser.getAuthorities(),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
        );
        this.opUser = opUser;
    }

    public User getOpUser() {
        return opUser;
    }
}