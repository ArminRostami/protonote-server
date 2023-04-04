package com.example.proto.models;

public class AuthUser {
    private String username;
    private String password;

    @Override
    public String toString() {
        return String.format("user=[username=%s password=%s]", getUsername(), getPassword());
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}