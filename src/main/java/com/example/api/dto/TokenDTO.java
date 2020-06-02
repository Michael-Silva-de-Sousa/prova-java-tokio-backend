package com.example.api.dto;

public class TokenDTO {

    private String login;
    private String token;

    public TokenDTO(String username, String token) {
        this.login = username;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
