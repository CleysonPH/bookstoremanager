package com.cleysonph.bookstoremanager.users.builder;

import com.cleysonph.bookstoremanager.user.dto.JwtRequest;

import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String username = "cleysonph";

    @Builder.Default
    private String password = "senha@123";

    public JwtRequest buildJwtRequest() {
        return new JwtRequest(username, password);
    }

}
