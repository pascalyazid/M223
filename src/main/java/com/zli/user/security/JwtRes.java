package com.zli.user.security;

public class JwtRes {
    private String token;

    public JwtRes(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
