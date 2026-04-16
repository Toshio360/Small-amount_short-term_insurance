package com.example.toshio.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import org.springframework.stereotype.Service;

import com.example.toshio.client.api.DefaultApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOidcUserService extends OidcUserService {

        @Autowired
        private DefaultApi api;

        @Override
        public OidcUser loadUser(OidcUserRequest userRequest) {
                // まず OIDC の標準ユーザーを取得
                OidcUser oidcUser = super.loadUser(userRequest);

                // Keycloak の preferred_username を取得
                String username = oidcUser.getPreferredUsername();

                // DB ユーザー取得
                var dbUser = api.userUsernameGet(username);

                // CustomOidcUser に差し替え
                return new CustomOidcUser(oidcUser, dbUser);
        }
}
