package edu.school21.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private Boolean identity = false;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIdentity() {
        return identity;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdentity(Boolean identity) {
        this.identity = identity;
    }
}
